drop table if exists anti_query_log;
drop table if exists tblSerialNumber;
drop table if exists tblMaterial;
drop table if exists report_info;

create table anti_query_log (
	id varchar(32),
	lable_no varchar(32),
	phone_no varchar(32),
	user_name varchar(64),
	query_time varchar(20),
	ip varchar(64),
	material_no varchar(256),
	is_exist varchar(1),
	query_count int,
	client varchar(10),
    primary key (id)
);

CREATE TABLE [dbo].[tblSerialNumber](
	[SNO_SERIALNO] [nvarchar](18) NOT NULL,
	[SNO_BARNO] [nvarchar](18) NULL,
	[SNO_BOXNO] [nvarchar](18) NULL,
	[SNO_MATERIAL] [nvarchar](18) NULL,
	[SNO_CREATEDATE] [datetime] NULL,
	[SNO_DC] [nvarchar](6) NULL,
	[SNO_JV] [nvarchar](8) NULL,
	[SNO_COMPANY] [nvarchar](50) NULL,
	[SNO_PRODUCTIONLINE] [nvarchar](18) NULL,
	[SNO_PRODUCTIONLINENO] [nvarchar](2) NULL,
	[SNO_WORKER] [nvarchar](2) NULL,
	[SNO_PRODUCTIONORDER] [nvarchar](22) NULL,
	[SNO_MODIFYUSER] [nvarchar](10) NULL,
	[SNO_MODIFYDATE] [datetime] NULL,
	[SNO_PRODUCTINFO] [nvarchar](20) NULL
);

create table tblMaterial (
	MA_MATERIAL varchar(32),
	MA_ALIAS varchar(32),
	MA_DESCRIPTION varchar(32)
);

CREATE TABLE [dbo].[report_info](
	[id] [varchar](50) NOT NULL,
	[client_name] [varchar](100) NULL,
	[client_phone] [varchar](100) NULL,
	[sales_address] [varchar](1000) NULL,
	[sales_name] [varchar](100) NULL,
	[sales_phone] [varchar](100) NULL,
	[product_type] [varchar](100) NULL,
	[amount] [varchar](100) NULL,
	[label_no] [varchar](50) NULL,
	[create_time] [datetime] NULL,
	[material_no] [varchar](50) NULL,
	[client] [varchar](50) NULL,
	[report_type] [varchar](20) NULL,
	[ip] [varchar](128) NULL
) ;
