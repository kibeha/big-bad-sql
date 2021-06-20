create or replace package picklist as 

   procedure fetch_by_fifo(
      p_order_id  in    orders.id%type
    , p_cursor    out   sys_refcursor
   );

end picklist;
/
