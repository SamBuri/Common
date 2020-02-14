/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

open module com.saburi.Common {
    requires transitive javafx.controls;
    requires transitive javafx.graphics;
    requires transitive javafx.fxml;
     requires transitive java.sql;
    requires org.hibernate.orm.core;
    requires org.jboss.logging;
    requires javassist;
    requires net.bytebuddy;
    requires antlr;
    requires java.transaction;
    requires jandex;
    requires com.fasterxml.classmate;
    requires java.activation;
    requires dom4j;
    requires org.hibernate.commons.annotations;
    requires java.xml.bind;
    requires java.persistence;
    requires org.hibernate.orm.envers;
    requires org.hibernate.validator;
    requires java.validation;
    requires javax.el;
    requires com.sun.xml.bind;
    requires com.sun.xml.txw2;
    requires com.sun.istack.runtime;
    requires org.jvnet.staxex;
    requires com.sun.xml.fastinfoset;
    requires mysql.connector.java;
    requires protobuf.java;
    requires itextpdf;
    requires org.kordamp.desktoppanefx.core;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.ikonli.materialdesign;
    requires org.apache.logging.log4j;
    requires org.apache.logging.log4j.core;
    requires javafx.swing;
    requires fontawesomefx;
    
    exports com.saburi.common.main;
    exports com.saburi.common.entities;
    exports com.saburi.common.dbaccess;
    exports com.saburi.common.utils;
    exports com.saburi.common.controllers;
}
