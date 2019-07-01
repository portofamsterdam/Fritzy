/*
 (C) COPYRIGHT TECHNOLUTION BV, GOUDA NL
| =======          I                   ==          I    =
|    I             I                    I          I
|    I   ===   === I ===  I ===   ===   I  I    I ====  I   ===  I ===
|    I  /   \ I    I/   I I/   I I   I  I  I    I  I    I  I   I I/   I
|    I  ===== I    I    I I    I I   I  I  I    I  I    I  I   I I    I
|    I  \     I    I    I I    I I   I  I  I   /I  \    I  I   I I    I
|    I   ===   === I    I I    I  ===  ===  === I   ==  I   ===  I    I
|                 +---------------------------------------------------+
+----+            |  +++++++++++++++++++++++++++++++++++++++++++++++++|
     |            |             ++++++++++++++++++++++++++++++++++++++|
     +------------+                          +++++++++++++++++++++++++|
                                                        ++++++++++++++|
                                                                 +++++|
 */
package nl.technolution.dropwizard.services;

/**
 * Service to be registered by Fritzy dropwizard app
 * 
 * @param <T> Object type used to init serivce
 */
public interface IService<T> {

    /**
     * init service
     * 
     * @param config
     */
    void init(T config);

    /**
     * deinitialize service, called at program end and can be used to close resources etc.
     * 
     * @param config
     */
    // TODO WHO: enable this (and add it to all the implementating classes...
    // void deInit();
}
