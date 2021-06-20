create or replace package body picklist as

   procedure fetch_by_fifo(
      p_order_id  in    orders.id%type
    , p_cursor    out   sys_refcursor
   ) as
   begin
      open p_cursor
      for
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
            where o.id = p_order_id
         )
         where acc_prv_qty < order_qty
      )
      order by
         warehouse, aisle#
       , case
            when mod(aisle#, 2) = 1 then +position
                                    else -position
         end;

   end fetch_by_fifo;

end picklist;
/
