/**********************************************************************
* Fichero interfaz del modulo GestfarmDrones
* Se declaran las funciones que seran utilizadas desde GestWarehouses
* David L . Version 3.4 - 05/02/2021 - se añade la función deleteData(int dbs)
***********************************************************************/
#pragma once

typedef char cString[20];

typedef struct type_DroneDB{
int total_patients;
int total_orders;
int daysBetweenOrders;
int total_sch_orders;
int total_routes;
void createPatient(int dbs);
void locatePatients(int dbs);
void newOrder(int dbs);
void listOrders(int dbs, cString WareDesc);
void schDailyOrders(int dbs);
void initializePatientsDB(int dbs);
void initializeOrdersDB(int dbs);
void listInitializedOrders(int dbs);
void deleteData(int dbs);
};
