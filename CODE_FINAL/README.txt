Source Code - Pro Spring Dynamic Modules for OSGi Service Platforms
------------------------------------------------------------------------

Contents 
---------------------
* ChapterXX directories - Source code for each chapter 
* equinox - Equinox OSGi framework (Copied automatically by each chapter script)
* felix - Felix OSGi framework (Copied automatically by each chapter script)
* lib - JAR (compile) dependencies used by source code (Copied automatically by each chapter script)
* osgilib - Bundle (run-time) dependencies used by applications (Copied automatically by each chapter script)


Pre-requisites
------------------------------------------
JDK 5 or greater - All code was tested using JDK 5 and JDK 6 from Sun (on both Linux(Ubuntu) and Windows(Vista))
Apache Ant - All code builds were tested using Apache Ant 1.6 and Ant 1.7
* Other pre-requisites are either included in this download or indicated for each chapter


Instructions - Compiling and running applications 
---------------------------------------------------------------
* Each Chapter's source code is contained in a top level directory. 
   Chapter01 - Chapter 1 source code  
   Chapter02 - Chapter 2 source code 
   Chapter03 - Chapter 3 source code 
   etc.

* Each directory contains an Ant Script that compiles and prepares the source code.
  It can be invoked with the following command:
   ant ch1 - Compile and prepare Chapter 1 source code
   ant ch2 - Compile and prepare Chapter 2 source code
   ant ch3 - Compile and prepare Chapter 3 source code
   etc.

* Once prepared, each Chapter's application can be located and executed within its own directory -- unless otherwise specified -- with the following commands: 

   Chapter 1 - Inside the felix sub-directory
      java -jar bin/felix.jar 

   Chapter 2 - Requires downloading and installing standard Apache Tomcat and MySQL database 
      Executable WAR available at : dist/ch2/springhelloworld.war 
      MySQL setup scripts available in mysql directory
      Tomcat dependency JARs available in tomcat_jar_deps directory
      (* See book Chapter for further instructions *)
      
   Chapter 3 - Inside the equinox sub-directory
      java -jar equinox.jar 
      
   Chapter 5 - Requires downloading and installing SpringSource dm server (NOTE: Requires same MySQL database used in Ch 2)
      Executable PAR available at : dist/ch2/helloworld.par 
      SpringSource dm server dependency bundles available in springsource_dm_server_bundle_deps directory
      (* See book Chapter for further instructions *)
  
   Chapter 7 - Inside the equinox sub-directory (NOTE: Requires same MySQL database used in Ch 2 and Ch 5)
      java -jar equinox.jar 
      (NOTE: Apache Ivy configuration files included, but not active. See book Chapter for further instructions *)

   Chapter 8 - Inside the equinoxXXX sub-directories (NOTE: Requires same MySQL database used in Ch 2, Ch 5 and Ch 7)
      For SSL sample, inside equinoxssl sub-directory:  java -jar equinox.jar 
           (NOTE: You need to create a keystore to run SSL, see ssl directory and book Chapter for further instructions)
      For Jetty sample, inside equinoxjetty sub-directory:  java -jar equinox.jar 
      For Flex sample, inside equinoxflex sub-directory:  java -jar equinox.jar 

   Chapter 9 - Application tests run with the same 'ant ch9' command. (NOTE: Requires same MySQL database used in Ch 2, Ch 5, Ch 7 and Ch8)
      (NOTE: Apache Ivy repository included to facilitate testing, but not active. See book Chapter for further instructions)
