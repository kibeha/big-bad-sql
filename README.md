# Who's afraid of the Big Bad SQL?

This repository accompanies the presentation "Who's afraid of the Big Bad SQL?" ( https://bit.ly/bigbadsql_pptx ) by Kim Berg Hansen.

This is purely for demonstration. Use it at your own risk and be sure to understand it before using.

# Usage

* Get the schema installation scripts from https://github.com/kibeha/practical-oracle-sql repository with source code for the Practical Oracle SQL book.

* Create and fill the demo schema using practical_create_schema.sql and practical_fill_schema.sql from the practical-oracle-sql repository.

* Install package PICKLIST in schema PRACTICAL using PICKLIST.spc and PICKLIST.bdy from db-source folder in this repository.

* Have a look at and play with queries in picklist_queries.sql in db-source folder in this repository.

* Make an Eclipse workspace for this (or use your own) and import the 3 Maven projects fifo.picking.v*

* Alter DB server IP and port in hibernate.cfg.xml (fifo.picking.v1 and fifo.picking.v2) and DataSourceUtil.java (fifo.picking.v3)

* Project fifo.picking.v1 contains a relatively simple Hibernate version to create the picklist.

* Project fifo.picking.v2 contains a Hibernate version that's augmented to produce better queries.

* Project fifo.picking.v3 contains a simple JDBC version that just calls the PICKLIST package in the database.

* For all 3 projects, the picklist for order 421 is generated to console output by running ExecPickList.java.

# Disclaimer

I am a beginner in Hibernate - please don't laugh if this is embarassingly simple ;-)

