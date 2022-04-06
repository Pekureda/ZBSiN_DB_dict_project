======================================================
Oracle Free Use Terms and Conditions (FUTC) License 
======================================================
https://www.oracle.com/downloads/licenses/oracle-free-license.html
===================================================================

ojdbc11-full.tar.gz - JDBC Thin Driver and Companion JARS
========================================================
This TAR archive (ojdbc11-full.tar.gz) contains the 21.1 release of the Oracle JDBC Thin driver(ojdbc11.jar), the Universal Connection Pool (ucp.jar) and other companion JARs grouped by category. 

(1) ojdbc11.jar (5,149,789 bytes) - 
(SHA1 Checksum: 387d2741de2dea00dd8bd7af451c4e3801320832)
Oracle JDBC Driver compatible with JDK11, JDK12, JDK13, JDK14, and JDK15; 
(2) ucp11.jar (1,793,946 bytes) - (SHA1 Checksum: 9918aba90d2c317bf9771a59e5166d872421e7f1)
Universal Connection Pool classes for use with JDK8, JDK9, and JDK11 -- for performance, scalability, high availability, sharded and multitenant databases. 
(3) rsi.jar (345,038 bytes) - (SHA1 Checksum: 17fe2b1bc664813082c30859d7abced439a374ca)
Reactive Streams Ingestion (RSI) - A dedicated path for ingesting high volume of data into Oracle database
(4) ojdbc.policy (11,515 bytes) - Sample security policy file for Oracle Database JDBC drivers

======================
Security Related JARs
======================
Java applications require some additional jars to use Oracle Wallets. 
You need to use all the three jars while using Oracle Wallets. 

(5) oraclepki.jar (307,411 bytes) - (SHA1 Checksum: 8d853bdef820911058cc9459ff11e463e410e36c)
Additional jar required to access Oracle Wallets from Java
(6) osdt_cert.jar (210,841 bytes) - (SHA1 Checksum: 44e21656566b5fdadc1704f8c1c58ad5759836bc)
Additional jar required to access Oracle Wallets from Java
(7) osdt_core.jar (312,695 bytes) - (SHA1 Checksum: 1725a9e9c6cf9e8ad6240a111de1e26b5b86ae96)
Additional jar required to access Oracle Wallets from Java

=============================
JARs for NLS and XDK support 
=============================
(8) orai18n.jar (1,664,456 bytes) - (SHA1 Checksum: a073ce669c94e444c45036c0e528eb03fb30a46e) 
Classes for NLS support
(9) xdb.jar (265,864 bytes) - (SHA1 Checksum: 94758239a7e7c508bf31ae3f5fe806bb3d228ab7)
Classes to support standard JDBC 4.x java.sql.SQLXML interface 
(10) xmlparserv2.jar (1,953,728 bytes) - (SHA1 Checksum: a8a57180ffe5f14a26927c4a6478b0f3bd3ad420)
Classes to support standard JDBC 4.x java.sql.SQLXML interface 
====================================================
JARs for Real Application Clusters(RAC), ADG, or DG 
====================================================
(11) ons.jar (198,464 bytes) - (SHA1 Checksum: 4d855651e215a1fc6d82555790c812c3d4f84f31)
for use by the pure Java client-side Oracle Notification Services (ONS) daemon
(12) simplefan.jar (32,204 bytes) - (SHA1 Checksum: 4e0c68aaae6b99fb210b5e829004ae2f30786790)
Java APIs for subscribing to RAC events via ONS; simplefan policy and javadoc

=================
USAGE GUIDELINES
=================
Refer to the JDBC Developers Guide (https://docs.oracle.com/en/database/oracle/oracle-database/21/jjdbc/index.html) and 
Universal Connection Pool Developers Guide (https://docs.oracle.com/en/database/oracle/oracle-database/21/jjucp/index.html)for more details.