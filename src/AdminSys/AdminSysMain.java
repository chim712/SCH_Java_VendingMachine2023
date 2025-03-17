package AdminSys;

import AdminSys.system.AdminSysLobby;
import AdminSys.system.Sales.DataAnalysis;
import AdminSys.system.Sales.SalesMain;

import javax.swing.*;

public class AdminSysMain extends JFrame {
    private SalesMain salesMain;
    public AdminSysLobby adminSysLobby;

    public AdminSysMain(){
        salesMain = new SalesMain();
        adminSysLobby = new AdminSysLobby();
        System.out.println("AdminSys Open");
        System.out.println(DataAnalysis.getDaySales(Time.getToday()));

    }
}
