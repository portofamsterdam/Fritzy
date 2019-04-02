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
package nl.technolution.batty.xstorage.types;

/**
 * 
 */
public enum EWaringType2 {
    // Bit 31 Error Code 31
    ERRORCODE31(31),
    // Bit 30 Error Code 30
    ERRORCODE30(30),
    // Bit 29 Grid Fac Fail Grid frequency is out of grid code range
    GRIDFACFAILGRIDFREQUENCYISOUTOFGRIDCODERANGE(29),
    // Bit 28 Battery OV reached Battery reach over voltage point
    BATTERYOVREACHEDBATTERYREACHOVERVOLTAGEPOINT(28),
    // Bit 27 Battery UV reached Battery reach under voltage point
    BATTERYUVREACHEDBATTERYREACHUNDERVOLTAGEPOINT(27),
    // Bit 26 Over Load The load level is exceeded
    OVERLOADTHELOADLEVELISEXCEEDED(26),
    // Bit 25 Error Code 25
    ERRORCODE25(25),
    // Bit 24 Over Power
    OVERPOWER(24),
    // Bit 23 Error Code 23
    ERRORCODE23(23),
    // Bit 22 Error Code 22
    ERRORCODE22(22),
    // Bit 21 Error Code 21
    ERRORCODE21(21),
    // Bit 20 Error Code 20
    ERRORCODE20(20),
    // Bit 19 Error Code 19
    ERRORCODE19(19),
    // Bit 18 Zpv PE Fail Isolation resistance of PV panel out of tolerable range before connecting to the grid
    ZPVPEFAILISOLATIONRESISTANCEOFPVPANELOUTOFTOLERABLERANGEBEFORECONNECTINGTOTHEGRID(18),
    // Bit 17 Grid Vac Fail Grid voltage is out of grid code range
    GRIDVACFAILGRIDVOLTAGEISOUTOFGRIDCODERANGE(17),
    // Bit 16 Reserved Fan Lock warning
    RESERVEDFANLOCKWARNING(16),
    // Bit 15 Vpv Max Fail PV input voltage is over the maximum tolerable value
    VPVMAXFAILPVINPUTVOLTAGEISOVERTHEMAXIMUMTOLERABLEVALUE(15),
    // Bit 14 Test Fail Auto Test failed
    TESTFAILAUTOTESTFAILED(14),
    // Bit 13 Temperature Fail The temperature is over the maximum tolerable value
    TEMPERATUREFAILTHETEMPERATUREISOVERTHEMAXIMUMTOLERABLEVALUE(13),
    // Bit 12 M-S Version Fail Master and Slave firmware version is mismatch
    MSVERSIONFAILMASTERANDSLAVEFIRMWAREVERSIONISMISMATCH(12),
    // Bit 11 Error Code 11 Error code 11
    ERRORCODE11ERRORCODE11(11),
    // Bit 10 RCMU Curr Fail Residual current is too high
    RCMUCURRFAILRESIDUALCURRENTISTOOHIGH(10),
    // Bit 9 No Utility Grid voltage is lost
    NOUTILITYGRIDVOLTAGEISLOST(9),
    // Bit 8 No Battery Battery communication or connection is lost
    NOBATTERYBATTERYCOMMUNICATIONORCONNECTIONISLOST(8),
    // Bit 7 Error Code 7
    ERRORCODE7(7),
    // Bit 6 Error Code 6
    ERRORCODE6(6),
    // Bit 5 Error Code 5
    ERRORCODE5(5),
    // Bit 4 Reserved
    RESERVED_4(4),
    // Bit 3 Reserved
    RESERVED_3(3),
    // Bit 2 Error Code 2
    ERRORCODE2(2),
    // Bit 1 Error Code 1
    ERRORCODE1(1),
    // Bit 0 Error Code 0
    ERRORCODE0(0);

    private final int bitNo;

    EWaringType2(int bitNo) {
        this.bitNo = bitNo;
    }

}
