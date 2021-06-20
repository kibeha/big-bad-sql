-- Order to be picked

variable p_order_id number

begin
   :p_order_id := 421;
end;
/

-- Data for order

select
   o.id     as order_id
 , c.name   as customer_name
 , ol.product_id
 , p.name as product_name
 , ol.qty   as order_qty
from orders o
join orderlines ol
   on ol.order_id = o.id
join customers c
   on c.id = o.customer_id
join products p
   on p.id = ol.product_id
where o.id = :p_order_id
order by ol.product_id;

-- Join with inventory in purchased date order (FIFO)

select
   o.id     as order_id
 , c.name   as customer_name
 , ol.product_id
 , p.name as product_name
 , ol.qty   as order_qty
 , i.qty    as loc_qty
 , pu.purchased
 , l.warehouse
 , l.aisle
 , l.position
from orders o
join orderlines ol
   on ol.order_id = o.id
join customers c
   on c.id = o.customer_id
join products p
   on p.id = ol.product_id
join inventory i
   on i.product_id = p.id
join purchases pu
   on pu.id = i.purchase_id
join locations l
   on l.id = i.location_id
where o.id = :p_order_id
order by i.product_id, pu.purchased, i.qty;

-- Accumulated sum of inventory quantity

select
   o.id     as order_id
 , c.name   as customer_name
 , ol.product_id
 , p.name as product_name
 , ol.qty   as order_qty
 , i.qty    as loc_qty
 , sum(i.qty) over (
      partition by i.product_id
      order by pu.purchased, i.qty
      rows between unbounded preceding and current row
   )        as acc_qty
 , pu.purchased
 , l.warehouse
 , l.aisle
 , l.position
from orders o
join orderlines ol
   on ol.order_id = o.id
join customers c
   on c.id = o.customer_id
join products p
   on p.id = ol.product_id
join inventory i
   on i.product_id = p.id
join purchases pu
   on pu.id = i.purchase_id
join locations l
   on l.id = i.location_id
where o.id = :p_order_id
order by i.product_id, pu.purchased, i.qty;

-- Better: Accumulated sum of inventory quantity of PREVIOUS rows

select
   o.id     as order_id
 , c.name   as customer_name
 , ol.product_id
 , p.name as product_name
 , ol.qty   as order_qty
 , i.qty    as loc_qty
 , nvl(sum(i.qty) over (
      partition by i.product_id
      order by pu.purchased, i.qty
      rows between unbounded preceding and 1 preceding
   ), 0)    as acc_prv_qty
 , pu.purchased
 , l.warehouse
 , l.aisle
 , l.position
from orders o
join orderlines ol
   on ol.order_id = o.id
join customers c
   on c.id = o.customer_id
join products p
   on p.id = ol.product_id
join inventory i
   on i.product_id = p.id
join purchases pu
   on pu.id = i.purchase_id
join locations l
   on l.id = i.location_id
where o.id = :p_order_id
order by i.product_id, pu.purchased, i.qty;

-- Filter to keep only rows where the accumulated sum of previous do not yet fullfill the ordered quantity

select
   s.*
 , least(loc_qty, order_qty - acc_prv_qty) as pick_qty
from (
   select
      o.id     as order_id
    , c.name   as customer_name
    , ol.product_id
    , p.name as product_name
    , ol.qty   as order_qty
    , i.qty    as loc_qty
    , nvl(sum(i.qty) over (
         partition by i.product_id
         order by pu.purchased, i.qty
         rows between unbounded preceding and 1 preceding
      ), 0)    as acc_prv_qty
    , pu.purchased
    , l.warehouse
    , l.aisle
    , l.position
   from orders o
   join orderlines ol
      on ol.order_id = o.id
   join customers c
      on c.id = o.customer_id
   join products p
      on p.id = ol.product_id
   join inventory i
      on i.product_id = p.id
   join purchases pu
      on pu.id = i.purchase_id
   join locations l
      on l.id = i.location_id
   where o.id = :p_order_id
) s
where acc_prv_qty < order_qty
order by product_id, purchased, loc_qty;

-- Use DENSE_RANK to number visited aisles consecutively

select
   s.*
 , least(loc_qty, order_qty - acc_prv_qty) as pick_qty
 , dense_rank() over (
      order by warehouse, aisle
   ) as aisle#
from (
   select
      o.id     as order_id
    , c.name   as customer_name
    , ol.product_id
    , p.name as product_name
    , ol.qty   as order_qty
    , i.qty    as loc_qty
    , nvl(sum(i.qty) over (
         partition by i.product_id
         order by pu.purchased, i.qty
         rows between unbounded preceding and 1 preceding
      ), 0)    as acc_prv_qty
    , pu.purchased
    , l.warehouse
    , l.aisle
    , l.position
   from orders o
   join orderlines ol
      on ol.order_id = o.id
   join customers c
      on c.id = o.customer_id
   join products p
      on p.id = ol.product_id
   join inventory i
      on i.product_id = p.id
   join purchases pu
      on pu.id = i.purchase_id
   join locations l
      on l.id = i.location_id
   where o.id = :p_order_id
) s
where acc_prv_qty < order_qty
order by warehouse, aisle, position;

-- Utilize the visited aisle number to order odd aisles by position ascending, even aisles by position descending

select
   s2.*
from (
   select
      s.*
    , least(loc_qty, order_qty - acc_prv_qty) as pick_qty
    , dense_rank() over (
         order by warehouse, aisle
      ) as aisle#
   from (
      select
         o.id     as order_id
       , c.name   as customer_name
       , ol.product_id
       , p.name as product_name
       , ol.qty   as order_qty
       , i.qty    as loc_qty
       , nvl(sum(i.qty) over (
            partition by i.product_id
            order by pu.purchased, i.qty
            rows between unbounded preceding and 1 preceding
         ), 0)    as acc_prv_qty
       , pu.purchased
       , l.warehouse
       , l.aisle
       , l.position
      from orders o
      join orderlines ol
         on ol.order_id = o.id
      join customers c
         on c.id = o.customer_id
      join products p
         on p.id = ol.product_id
      join inventory i
         on i.product_id = p.id
      join purchases pu
         on pu.id = i.purchase_id
      join locations l
         on l.id = i.location_id
      where o.id = :p_order_id
   ) s
   where acc_prv_qty < order_qty
) s2
order by
   warehouse, aisle#
 , case
      when mod(aisle#, 2) = 1 then +position
                              else -position
   end;

-- Clean up the query to keep only the needed data for the picklist

select
   warehouse, aisle, position
 , product_name, pick_qty, order_id, customer_name
from (
   select
      warehouse, aisle
    , dense_rank() over (
         order by warehouse, aisle
      ) as aisle#
    , position
    , product_name, order_id, customer_name
    , least(loc_qty, order_qty - acc_prv_qty) as pick_qty
   from (
      select
         o.id     as order_id
       , c.name   as customer_name
       , p.name as product_name
       , ol.qty   as order_qty
       , i.qty    as loc_qty
       , nvl(sum(i.qty) over (
            partition by i.product_id
            order by pu.purchased, i.qty
            rows between unbounded preceding and 1 preceding
         ), 0)    as acc_prv_qty
       , pu.purchased
       , l.warehouse
       , l.aisle
       , l.position
      from orders o
      join orderlines ol
         on ol.order_id = o.id
      join customers c
         on c.id = o.customer_id
      join products p
         on p.id = ol.product_id
      join inventory i
         on i.product_id = p.id
      join purchases pu
         on pu.id = i.purchase_id
      join locations l
         on l.id = i.location_id
      where o.id = :p_order_id
   )
   where acc_prv_qty < order_qty
)
order by
   warehouse, aisle#
 , case
      when mod(aisle#, 2) = 1 then +position
                              else -position
   end;

-- That final query to be used in package body PICKLIST
