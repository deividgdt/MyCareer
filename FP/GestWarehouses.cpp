/************************************
* NOMBRE: #DAVID#
* PRIMER APELLIDO: ##
* SEGUNDO APELLIDO: ##
* DNI: ##
* EMAIL: ##
*************************************/

/****************************************************************************
* Fichero para el modulo de gestión de almacenes
* David L - Version 2.1 - 05/02/2021 - Se añade la función getWarehouseCode
*****************************************************************************/

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>
#include <windows.h>
#include "GestfarmDrones.h"

typedef char aString[49]; //cadena 1
typedef char bString[17]; //cadena 2
int total_warehouses,dbs; //dbs: Database Selected
char DBInitialize = 'n';

typedef struct typeWarehouse{
 int ref;
 int code; // codigo interno
 aString street;
 aString town;
 bString province;
 aString description;
 type_DroneDB drone;
};

typedef typeWarehouse v_Warehouse[10];
v_Warehouse warehouse;

typedef struct MgmtWarehouses{
  void createWarehouse();
  void createPatient();
  void locatePatient();
  void createOrder();
  void listDailyOrders();
  void scheduleDailyRoutes();
  void showDailyRoutes();
  void startMgmt();
};

int getWarehouseCode(int warehouseRef){
  for(int iwgc=1;iwgc<=total_warehouses;iwgc++){
    if(warehouseRef > 10 || warehouseRef < 1){
      printf("ERROR: Inserte un numero entre 1 y 10\n");
      return 0;
    } else {
       if(warehouseRef == warehouse[iwgc].ref){
         return warehouse[iwgc].code;
       }
    }
  }
  printf("ERROR: No existe el almacen %d.\n", warehouseRef);
  return 0;
}

void MgmtWarehouses::createWarehouse(){
  int warehouseCodeToDelete, warehouseRefSelected;
  char verify_wselection, asktodelete;
  int iw=total_warehouses;

  while( (verify_wselection != 's') && (verify_wselection != 'S') ){
    iw=iw+1;
    printf("\nAlta nuevo almacen:\n\n");
    if(total_warehouses >= 10){
      while( (asktodelete != 's') && (asktodelete != 'S') ){
        printf("ERROR: Ya existen 10 almacenes en la base de datos.\nEscriba el identificador del almacen que quiere dar de baja: "); scanf("%d", &warehouseCodeToDelete); fflush(stdin);
        printf("Esta seguro que desea eliminar este almacen (S/N)? "); scanf("%c", &asktodelete);
      }
      if(asktodelete == 's' || asktodelete == 'S'){warehouse[getWarehouseCode(warehouseCodeToDelete)].drone.deleteData(getWarehouseCode(warehouseCodeToDelete));}
    }

    printf("Identificador almacen (cod. de almacen 1 a 10)? "); scanf("%d", &warehouseRefSelected); fflush(stdin);
    if(warehouseRefSelected > 0 && warehouseRefSelected <= 10){
      fflush(stdin);
      warehouse[iw].ref = warehouseRefSelected;
      warehouse[iw].code = iw;
      printf("Direccion almacen (entre 1 y 48 caracteres)? "); scanf("%[^\n]s", &warehouse[iw].street); fflush(stdin);
      printf("Municipio almacen (entre 1 y 48 caracteres)? "); scanf("%[^\n]s", &warehouse[iw].town); fflush(stdin);
      printf("Provincia almacen (entre 1 y 48 caracteres)? "); scanf("%[^\n]s", &warehouse[iw].province); fflush(stdin);
      printf("Descripcion almacen (entre 1 y 48 caracteres)? "); scanf("%[^\n]s", &warehouse[iw].description); fflush(stdin);
      printf("Datos correctos (S/N)? "); scanf("%c", &verify_wselection);
      if((verify_wselection == 'n') || (verify_wselection == 'N')){iw--;}
    } else {
      printf("ERROR: Identificador incorrecto.\n");
      verify_wselection = 'n';
      iw--;
    }


  }
  total_warehouses=iw;
}

void MgmtWarehouses::createPatient(){
    int warehouseCode;

    printf("Alta nuevo paciente:\n\n");
    printf("\tCodigo almacen (entre 1-10)? "); scanf("%d", &warehouseCode);
    printf("\n");

    if(getWarehouseCode(warehouseCode) == 0){
      printf("ERROR: Identificador incorrecto.\n");
    } else {
      warehouse[getWarehouseCode(warehouseCode)].drone.createPatient(getWarehouseCode(warehouseCode));
    }


}

void MgmtWarehouses::locatePatient(){
    int warehouseCode;

    printf("Lista de pacientes y su ubicacion:\n\n");
    printf("\tCodigo almacen (entre 1-10)? "); scanf("%d", &warehouseCode);
    printf("\n");
    if(getWarehouseCode(warehouseCode) == 0){
      printf("ERROR: Identificador incorrecto.\n");
    } else {
      warehouse[getWarehouseCode(warehouseCode)].drone.locatePatients(getWarehouseCode(warehouseCode));
    }
}

void MgmtWarehouses::createOrder(){
    int warehouseCode;

    printf("Nuevo pedido:\n\n");
    printf("\tCodigo almacen (entre 1-10)? "); scanf("%d", &warehouseCode);
    printf("\n");

    if(getWarehouseCode(warehouseCode) == 0){
      printf("ERROR: Identificador incorrecto.\n");
    } else {
      warehouse[getWarehouseCode(warehouseCode)].drone.newOrder(getWarehouseCode(warehouseCode));
    }
}

void MgmtWarehouses::listDailyOrders(){
    int warehouseCode;

    printf("Lista diaria de pedidos:\n\n");
    printf("\tCodigo almacen (entre 1-10)? "); scanf("%d", &warehouseCode);
    printf("\n");

    if(getWarehouseCode(warehouseCode) == 0){
      printf("ERROR: Identificador incorrecto.\n");
    } else {
      warehouse[getWarehouseCode(warehouseCode)].drone.listOrders(getWarehouseCode(warehouseCode), warehouse[getWarehouseCode(warehouseCode)].description);
    }
}

void MgmtWarehouses::scheduleDailyRoutes(){
  int warehouseCode;

  printf("Programar rutas diarias del drone:\n");

  printf("\tCodigo almacen (entre 1-10)? "); scanf("%d", &warehouseCode);
  printf("\n");

  if(getWarehouseCode(warehouseCode) == 0){
    printf("ERROR: Identificador incorrecto.\n");
  } else {
    warehouse[getWarehouseCode(warehouseCode)].drone.schDailyOrders(getWarehouseCode(warehouseCode));
  }
}

void MgmtWarehouses::showDailyRoutes(){
  printf("En construccion... \n");
}

void MgmtWarehouses::startMgmt(){
    if (DBInitialize == 'n'){
    //Creamos los almacenes
      for(int iw = 1;iw<6;iw++){
        warehouse[iw].code = iw;
        warehouse[iw].ref = iw;
        switch(iw){
          case 1:strcpy(warehouse[iw].street, "Calzada Ategorrieta 25"); strcpy(warehouse[iw].town, "Donostia"); strcpy(warehouse[iw].province, "Guipuzcoa"); strcpy(warehouse[iw].description, "Ategorrieta 25"); break;
          case 2:strcpy(warehouse[iw].street, "Calle Rodriguez 1"); strcpy(warehouse[iw].town, "Donostia"); strcpy(warehouse[iw].province, "Guipuzcoa"); strcpy(warehouse[iw].description, "Rodriguez 1"); break;
          case 3:strcpy(warehouse[iw].street, "Avenida 107"); strcpy(warehouse[iw].town, "Madrid"); strcpy(warehouse[iw].province, "Madrid"); strcpy(warehouse[iw].description, "Numero 107"); break;
          case 4:strcpy(warehouse[iw].street, "Calle Francisco 20"); strcpy(warehouse[iw].town, "Madrid"); strcpy(warehouse[iw].province, "Madrid"); strcpy(warehouse[iw].description, "Francisco 20"); break;
          case 5:strcpy(warehouse[iw].street, "Carretera Siglo 21"); strcpy(warehouse[iw].town, "Madrid"); strcpy(warehouse[iw].province, "Madrid"); strcpy(warehouse[iw].description, "Siglo 21"); break;
        }
        total_warehouses++;
      }

      //creamos los pacientes y los pedidos
      system("cls");
      system("color 04");
      printf("Estamos generando los primeros datos aleatorios para su uso...\n");
      warehouse[1].drone.initializePatientsDB(1);
      warehouse[5].drone.initializePatientsDB(5);
      warehouse[1].drone.initializeOrdersDB(1);
      system("color 0f");
      system("cls");
      printf("Datos generados correctamente...\n\n");
      system("pause");

      // listamos los pacientes y los pedidos
      for(int itw=1;itw<total_warehouses+1;itw++){
        printf("Almacen %d - %s - %s - %s\n Descripcion: %s\n\n", warehouse[itw].ref, warehouse[itw].street, warehouse[itw].town, warehouse[itw].province, warehouse[itw].description);
        if(itw == 1){
          printf("Clientes\n\n");

          warehouse[itw].drone.locatePatients(itw);

          printf("Pedidos\n\n");

          //Listamos los pedidos en el almacen 1
          warehouse[itw].drone.listInitializedOrders(itw);

        } else if(itw == 5) {

          printf("Clientes\n\n");
          // Listamos los pacientes creados en el almacen 5
          warehouse[itw].drone.locatePatients(itw);
        }
      }
      DBInitialize = 'y';
    } else {
        printf("Las bases de datos ya han sido inicializadas.\n");
    }
}

void MainMenu(){
  MgmtWarehouses WarehousesDB;
  char selection;
  while( (selection != 's') && (selection != 'S')){
	  printf("\nGESTION DE FarmaDrones: Distribucion de Farmacos\n");
	  printf("\tIniciar gestion                     (Pulsar I)\n");
	  printf("\tAlta almacen                        (Pulsar M)\n");
	  printf("\tAlta paciente almacen               (Pulsar A)\n");
	  printf("\tUbicar pacientes                    (Pulsar U)\n");
	  printf("\tNuevo pedido                        (Pulsar N)\n");
	  printf("\tLista diaria de pedidos             (Pulsar L)\n");
	  printf("\tProgramar rutas diarias del drone   (Pulsar P)\n");
	  printf("\tRepresentar rutas diarias del drone (Pulsar R)\n");
	  printf("\tSalir                               (Pulsar S)\n");
	  printf("Teclear una opcion valida (I|M|A|U|N|L|P|R|S): ");
    scanf("%c", &selection);
    switch (selection) {
      case 'I':
        system("cls");
        WarehousesDB.startMgmt();
        break;
      case 'M':
        system("cls");
        WarehousesDB.createWarehouse();
        break;
      case 'A':
        system("cls");
        WarehousesDB.createPatient();
        break;
      case 'U':
        system("cls");
        WarehousesDB.locatePatient();
        break;
      case 'N':
        system("cls");
        WarehousesDB.createOrder();
        break;
      case 'L':
        system("cls");
        WarehousesDB.listDailyOrders();
        break;
      case 'P':
        system("cls");
        WarehousesDB.scheduleDailyRoutes();
        break;
      case 'R':
        system("cls");
        WarehousesDB.showDailyRoutes();
        break;
      case 'S':
        system("cls");
        break;
      default:
      ;
    }
    fflush(stdin);
	}
}

int main(){
MainMenu();
}
