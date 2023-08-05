/*
Navicat SQL Server Data Transfer

Source Server         : Local DBM
Source Server Version : 150000
Source Host           : localhost:1433
Source Database       : forum
Source Schema         : dbo

Target Server Type    : SQL Server
Target Server Version : 150000
File Encoding         : 65001

Date: 2023-08-05 11:21:28
*/


-- ----------------------------
-- Table structure for phones
-- ----------------------------
DROP TABLE [dbo].[phones]
GO
CREATE TABLE [dbo].[phones] (
[id] int NOT NULL IDENTITY(1,1) ,
[admin_id] int NOT NULL ,
[phone_number] varchar(20) NULL 
)


GO

-- ----------------------------
-- Table structure for post_likedBy
-- ----------------------------
DROP TABLE [dbo].[post_likedBy]
GO
CREATE TABLE [dbo].[post_likedBy] (
[post_id] int NOT NULL ,
[user_id] int NOT NULL 
)


GO

-- ----------------------------
-- Table structure for posts
-- ----------------------------
DROP TABLE [dbo].[posts]
GO
CREATE TABLE [dbo].[posts] (
[id] int NOT NULL IDENTITY(1,1) ,
[creator] int NULL ,
[likes] int NULL ,
[content] varchar(MAX) NOT NULL ,
[creation_date] datetime NOT NULL ,
[topic] int NULL ,
[status_deleted] tinyint NULL 
)


GO
DBCC CHECKIDENT(N'[dbo].[posts]', RESEED, 9)
GO

-- ----------------------------
-- Table structure for tags
-- ----------------------------
DROP TABLE [dbo].[tags]
GO
CREATE TABLE [dbo].[tags] (
[id] int NOT NULL IDENTITY(1,1) ,
[name] varchar(MAX) NOT NULL ,
[belongs_to] int NULL ,
[status_deleted] tinyint NULL ,
[creation_date] datetime NULL 
)


GO
DBCC CHECKIDENT(N'[dbo].[tags]', RESEED, 23)
GO

-- ----------------------------
-- Table structure for topic
-- ----------------------------
DROP TABLE [dbo].[topic]
GO
CREATE TABLE [dbo].[topic] (
[id] int NOT NULL IDENTITY(1,1) ,
[title] varchar(255) NULL ,
[content] varchar(MAX) NULL ,
[views] int NULL ,
[likes] int NULL ,
[creation_date] datetime NULL ,
[creator] int NULL ,
[status_deleted] tinyint NULL 
)


GO
DBCC CHECKIDENT(N'[dbo].[topic]', RESEED, 20)
GO

-- ----------------------------
-- Table structure for topic_likedBy
-- ----------------------------
DROP TABLE [dbo].[topic_likedBy]
GO
CREATE TABLE [dbo].[topic_likedBy] (
[topic_id] int NOT NULL ,
[user_id] int NOT NULL 
)


GO

-- ----------------------------
-- Table structure for topics_tags
-- ----------------------------
DROP TABLE [dbo].[topics_tags]
GO
CREATE TABLE [dbo].[topics_tags] (
[topic_id] int NOT NULL ,
[tag_id] int NOT NULL 
)


GO

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE [dbo].[users]
GO
CREATE TABLE [dbo].[users] (
[id] int NOT NULL IDENTITY(1,1) ,
[username] varchar(50) NOT NULL ,
[password] varchar(100) NOT NULL ,
[email] varchar(100) NOT NULL ,
[first_name] varchar(100) NULL ,
[last_name] varchar(100) NULL ,
[user_level] tinyint NULL ,
[phone_number] varchar(100) NULL ,
[avatar] varchar(MAX) NULL ,
[verified] int NULL 
)


GO
DBCC CHECKIDENT(N'[dbo].[users]', RESEED, 1091)
GO

-- ----------------------------
-- Table structure for users_verification
-- ----------------------------
DROP TABLE [dbo].[users_verification]
GO
CREATE TABLE [dbo].[users_verification] (
[id] int NOT NULL IDENTITY(1,1) ,
[token] varchar(255) NULL ,
[user_id] int NOT NULL 
)


GO

-- ----------------------------
-- Indexes structure for table phones
-- ----------------------------

-- ----------------------------
-- Primary Key structure for table phones
-- ----------------------------
ALTER TABLE [dbo].[phones] ADD PRIMARY KEY ([admin_id])
GO

-- ----------------------------
-- Indexes structure for table posts
-- ----------------------------

-- ----------------------------
-- Primary Key structure for table posts
-- ----------------------------
ALTER TABLE [dbo].[posts] ADD PRIMARY KEY ([id])
GO

-- ----------------------------
-- Indexes structure for table tags
-- ----------------------------

-- ----------------------------
-- Primary Key structure for table tags
-- ----------------------------
ALTER TABLE [dbo].[tags] ADD PRIMARY KEY ([id])
GO

-- ----------------------------
-- Indexes structure for table topic
-- ----------------------------

-- ----------------------------
-- Primary Key structure for table topic
-- ----------------------------
ALTER TABLE [dbo].[topic] ADD PRIMARY KEY ([id])
GO

-- ----------------------------
-- Indexes structure for table users
-- ----------------------------

-- ----------------------------
-- Primary Key structure for table users
-- ----------------------------
ALTER TABLE [dbo].[users] ADD PRIMARY KEY ([id])
GO

-- ----------------------------
-- Foreign Key structure for table [dbo].[post_likedBy]
-- ----------------------------
ALTER TABLE [dbo].[post_likedBy] ADD FOREIGN KEY ([post_id]) REFERENCES [dbo].[posts] ([id]) ON DELETE NO ACTION ON UPDATE NO ACTION
GO
ALTER TABLE [dbo].[post_likedBy] ADD FOREIGN KEY ([user_id]) REFERENCES [dbo].[users] ([id]) ON DELETE NO ACTION ON UPDATE NO ACTION
GO

-- ----------------------------
-- Foreign Key structure for table [dbo].[posts]
-- ----------------------------
ALTER TABLE [dbo].[posts] ADD FOREIGN KEY ([topic]) REFERENCES [dbo].[topic] ([id]) ON DELETE NO ACTION ON UPDATE NO ACTION
GO
ALTER TABLE [dbo].[posts] ADD FOREIGN KEY ([creator]) REFERENCES [dbo].[users] ([id]) ON DELETE NO ACTION ON UPDATE NO ACTION
GO

-- ----------------------------
-- Foreign Key structure for table [dbo].[tags]
-- ----------------------------
ALTER TABLE [dbo].[tags] ADD FOREIGN KEY ([belongs_to]) REFERENCES [dbo].[users] ([id]) ON DELETE NO ACTION ON UPDATE NO ACTION
GO

-- ----------------------------
-- Foreign Key structure for table [dbo].[topic]
-- ----------------------------
ALTER TABLE [dbo].[topic] ADD FOREIGN KEY ([creator]) REFERENCES [dbo].[users] ([id]) ON DELETE NO ACTION ON UPDATE NO ACTION
GO

-- ----------------------------
-- Foreign Key structure for table [dbo].[topic_likedBy]
-- ----------------------------
ALTER TABLE [dbo].[topic_likedBy] ADD FOREIGN KEY ([topic_id]) REFERENCES [dbo].[topic] ([id]) ON DELETE NO ACTION ON UPDATE NO ACTION
GO
ALTER TABLE [dbo].[topic_likedBy] ADD FOREIGN KEY ([user_id]) REFERENCES [dbo].[users] ([id]) ON DELETE NO ACTION ON UPDATE NO ACTION
GO

-- ----------------------------
-- Foreign Key structure for table [dbo].[topics_tags]
-- ----------------------------
ALTER TABLE [dbo].[topics_tags] ADD FOREIGN KEY ([tag_id]) REFERENCES [dbo].[tags] ([id]) ON DELETE NO ACTION ON UPDATE NO ACTION
GO
ALTER TABLE [dbo].[topics_tags] ADD FOREIGN KEY ([topic_id]) REFERENCES [dbo].[topic] ([id]) ON DELETE NO ACTION ON UPDATE NO ACTION
GO

-- ----------------------------
-- Foreign Key structure for table [dbo].[users_verification]
-- ----------------------------
ALTER TABLE [dbo].[users_verification] ADD FOREIGN KEY ([user_id]) REFERENCES [dbo].[users] ([id]) ON DELETE NO ACTION ON UPDATE NO ACTION
GO
