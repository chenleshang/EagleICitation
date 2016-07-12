# DataCitation

/**
* TestJena 2.2 0613
* Based on discussion with Faith. 
* TestJena 2.1 0524
* Changed XML display. 
* TestJena 2.0 0515
* Full Version of Data Citation. 
* Notice:
* Removed Normal Citation Form.
* Only output JSON Format. 
* This part contains the path of compact RDF data file
* and a certain path for Jena to populate the file into 
* a real database directory. 
* We must set proper path in Windows/Linux/OSX as they 
* have different file system. 
*/
	 
General Instructions on How to Set Up the Environment
1\ Install JDK and Eclipse. 
2\ Download, unzip Apache Tomcat 8.0 on computer, configure Tomcat server in Eclipse. 
3\ Import project to Eclipse. Library files contained in project. 
4\ Unzip database files on local computer. Change path variable in TestJena.java (src) to related Win/Linux/OSX path on local host. 
5\ Release comment on populateTDB() lines. 
6\ Run TestJena.java as Java Application to populate database. 
7\ Comment populateTDB();
8\ Run whole project on sever. Define a new Tomcat server for it. 