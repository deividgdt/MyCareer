/*************************************************************************
* Modulo encargado de la gestion de todos los Drones por almacen
* David L . Version 3.4 - 05/02/2021 - se añade la función deleteData()
**************************************************************************/
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>
#include <math.h>
#include <time.h>
#include <windows.h>
#include "CalculoFechas.h"
#include "GestfarmDrones.h"

/**********************************************************************
Variables y tipos globales que necesitan ser declarados aqui para poder
ser usados en todos los scopes.
***********************************************************************/
int total_patients, total_orders, daysBetweenOrders, total_sch_orders, total_routes;
float distanceBetweenAxis, angleBetweenAxis;
typedef char aString[21];
typedef int typeDistanceByStops[21];

/**********************************************************************
Tipo struct que crea el "objeto" typeOrderDate con sus variables para
almacenar las fechas de los pedidos.
***********************************************************************/
typedef struct typeOrderDate{
  int day;
  int month;
  int year;
};

/**********************************************************************
Tipo struct que crea el "objeto" gpsLocation con sus variables para
almacenar las coordenadas.
***********************************************************************/
typedef struct gpsLocation{
	int distance;
	float angle;
	float x;
	float y;
};

/**********************************************************************
Tipo struct que crea el "objeto" typePatients con sus variables para los
pacientes. Contiene un vector de caracteres "aString" y tipo struct que
contiene las coordenadas de los pacientes "gpsLocation"
practica 5 - Se añaden dos variables que almacenaran coordenadas (x,y)
***********************************************************************/
typedef struct typePatients{
	int ref;
	aString ID;
	gpsLocation gps_position;
};
typedef typePatients v_patients[20];

typedef struct type_PatientsDB{
  v_patients patient;
};
typedef type_PatientsDB vType_PatientsDB[10];
vType_PatientsDB patientDB;

/**********************************************************************
Tipo struct que crea el "objeto" typeDrug con sus variables para los
farmacos. Contiene un vector de caracteres "aString"
***********************************************************************/
typedef struct typeDrug{
  aString name;
  int weight;
  int units;
};

typedef typeDrug v_drugsByOrder[5];

/**********************************************************************
Tipo struct que crea el "objeto" typeOrders con sus variables para los
pedidos. Se llama un tipo struct de fecha "date" y farmacos "drugs"
***********************************************************************/
typedef struct typeOrders{
	int ref;
	int totalDrugs;
	int totalWeight;
	int totalDistance;
	int refPatient;
	int numbers;
	typeOrderDate date;
	v_drugsByOrder drugs;
};
typedef typeOrders v_orders[100];

typedef struct type_OrdersDB{
  v_orders orders;
};
typedef type_OrdersDB vType_OrdersDB[10];
vType_OrdersDB ordersDB;

/**********************************************************************
Tipo struct que crea el "objeto" typeSchOrders para almacenar los
pedidos programados.
***********************************************************************/
typedef struct typeSchOrders{
  bool assigned;
  int code;
  int patientRef;
  int totalWeight;
  float angle;
  aString clientID;
  float client_coor_X;
  float client_coor_Y;
};
typedef typeSchOrders v_TypeSchOrders[100];

typedef struct type_SchOrdersDB{
  v_TypeSchOrders schOrders;
};
typedef type_SchOrdersDB vType_SchOrdersDB[10];
vType_SchOrdersDB schOrdersDB;


/**********************************************************************
Tipo struct que crea el "objeto" typeRoutes con sus variables para las
rutas.
***********************************************************************/
typedef struct typeRoutes{
  int routeID;
  int totalWeight;
  int totalDistance;
  int totalStops;
  typeDistanceByStops distanceByStops;
  v_TypeSchOrders order;
  bool completed;
};
typedef typeRoutes v_TypeRoutes[100];

typedef struct type_routesDB{
  v_TypeRoutes routes;
};
typedef type_routesDB vType_routesDB[10];
vType_routesDB routesDB;

/**********************************************************************
Funcion que calcula la distancia entre dos puntos en el plano
cartesiano
***********************************************************************/
void f_distanceBetweenAxis(float xa, float ya, float xb, float yb){
    distanceBetweenAxis = sqrt(pow((xb - xa), 2) + pow((yb - ya), 2));
}

/**********************************************************************
Funcion que calcula el angulo entre dos puntos en el plano cartesiano.
***********************************************************************/
void f_angleBetweenPatients(float xa, float ya, float xb, float yb, float anglea){
  float m;

  //calculamos la pendiente
  m = (yb-ya)/(xb-xa);
  // calculamos el angulo entre dos puntos usando la pendiente m (yb-ya)/(xb-xa)
  angleBetweenAxis = atan(m)*180/3.1415;
  // si es la vuelta al almacen
  if(xb == 0 && yb == 0){
    if (anglea >= 0 && anglea < 501) {
      angleBetweenAxis = 180 + angleBetweenAxis;
    }else if (anglea > 500 && anglea < 1001){
      angleBetweenAxis = 180 - angleBetweenAxis;
    }else if(anglea > 1000 && anglea < 1501){
      angleBetweenAxis = angleBetweenAxis ;
    }else{
      angleBetweenAxis = 360 - angleBetweenAxis;
    }
  // si no es la vuelta al almacen
  } else {
    // si la pendiente es menor que 0
    if( m < 0){
      angleBetweenAxis = 360+angleBetweenAxis;}
    // si no es menor que 0
    else{
      angleBetweenAxis = 180+angleBetweenAxis;
      }
  }

  angleBetweenAxis = (angleBetweenAxis*1000/180);
}

/**********************************************************************
Funcion que se encarga de comprobar si un pedido entra en una ruta oç
no. Se realizan los siguientes chequeos al añadir el pedido:
1.- Que el peso no sea mayor a 3000
2.- Que la suma de la distancia total del pedido mas la suma del la
    distancia entre ejes no sea mayor a la MaximaDistancia la cual
    se calcula en base al peso
3.- Que se pueda volver al almacen desde este punto en caso de que
    haya pasado las dos condiciones anteriores.
***********************************************************************/
bool checkIfCanAddIn(typeRoutes routes, typeSchOrders orders, int counter){
  float goBackHome;
  float MaxDistance;
  float TW = routes.totalWeight+orders.totalWeight;

  // si la suma del peso total de la ruta mas el peso total del pedido es menor que 3000
  if (TW <= 3000){
    // calculamos de la distancia maxima que puede recorrer el drone dependiendo del peso
    MaxDistance = ((((TW-3000) / -3000)*5000)+20000);
    // Comprobamos si la distancia total de la ruta mas la nueva distancia es menor que la Maximadistancia
    f_distanceBetweenAxis(routes.order[routes.totalStops-1].client_coor_X,routes.order[routes.totalStops-1].client_coor_Y,orders.client_coor_X,orders.client_coor_Y);

    if(routes.totalDistance+distanceBetweenAxis <= MaxDistance){

      // Comprobamos si el Drone puede volver a casa desde aqui
      goBackHome = routes.totalDistance+distanceBetweenAxis;
      f_distanceBetweenAxis(orders.client_coor_X,orders.client_coor_Y,0,0);
      if(goBackHome+distanceBetweenAxis <= MaxDistance){
        return true;
      } else {
        return false;
      }
    }
    else {
      return false;
    }
  }
  else {
    return false;
  }
}

/**********************************************************************
Funcion createPatient que da de alta los pacientes.
***********************************************************************/
void type_DroneDB::createPatient(int dbs){
	int i=total_patients; // variable para controlar el total de pacientes.
  char patient_selection='s'; // variable para controlar la seleccion del usuario si desea introducir otro paciente
  char verify_selection='s'; // variable para controlar la seleccion del usuario si confirma los datos

 	while ( patient_selection != 'n' && patient_selection != 'N') {
	    if ( total_patients < 21){ // condición para limitar los pacientes a 20
        i=i+1;
        printf("\tDatos paciente:\n");
        patientDB[dbs].patient[i].ref = i; // asignamos un valor unico a cada paciente
        printf("\tIdentificador (entre 1 y 20 caracteres)? "); fflush(stdin); scanf("%[^\n]s",&patientDB[dbs].patient[i].ID);
        printf("\tDistancia (hasta 10000 metros a plena carga)? "); fflush(stdin); scanf("%d",&patientDB[dbs].patient[i].gps_position.distance);
        printf("\tAngulo(entre 0 y 2000 milesimas de pi radianes)? "); fflush(stdin); scanf("%f",&patientDB[dbs].patient[i].gps_position.angle);
        printf("\n\n");

        // guardamos las del paciente pasandolas de forma polar a forma cartesiana
        patientDB[dbs].patient[i].gps_position.x = patientDB[dbs].patient[i].gps_position.distance * cos(patientDB[dbs].patient[i].gps_position.angle*3.1415/1000);
        patientDB[dbs].patient[i].gps_position.y = patientDB[dbs].patient[i].gps_position.distance * sin(patientDB[dbs].patient[i].gps_position.angle*3.1415/1000);

        fflush(stdin);
        printf("\tDatos correctos (s/n): "); scanf("%c", &verify_selection);

        if ( (patientDB[dbs].patient[i].gps_position.distance > 10000) || (patientDB[dbs].patient[i].gps_position.distance < 0)){ // controlamos que la distancia no sea menor que 0m o mayor a 10km
          verify_selection = 'n';
          printf("\tERROR: Distancia debe ser mayor que 0 y menor que 10000. Ha seleccionado: %d\n", patientDB[dbs].patient[i].gps_position.distance);
        } else if ( (patientDB[dbs].patient[i].gps_position.angle > 2000) || (patientDB[dbs].patient[i].gps_position.angle < 0)){ // controlamos que el angulo no sea menor que 0 o mayor que 2000
          printf("\tERROR: Angulo debe ser mayor que 0 y menor que 2000. Ha seleccionado: %.0f\n", patientDB[dbs].patient[i].gps_position.angle);
          verify_selection = 'n';
        }

        if ( verify_selection == 'n' ){ // si los datos son incorrectos
          i=i-1; // reducimos uno a la variable i que controla el indice de creacion de pacientes
        } else {
          fflush(stdin);
          printf("\tOtro paciente mismo almacen(s/n): "); scanf("%c", &patient_selection);
          printf("\n\n");
        }
        total_patients=i;
	    } else { // si se intenta introducir un paciente más a partir de 20 en BBDD indicamos error
        printf("ERROR: No se permiten más pacientes en la base de datos.\n");
        patient_selection = 'n';
	    }

	}
}

/**********************************************************************
Funcion locatePatients que ubica a los pacientes.
***********************************************************************/
void type_DroneDB::locatePatients(int dbs){
	printf("Ref\tIdentificador\t\t\tDistancia\tAngulo\n");
 	for (int i=1;i<=total_patients;i++) { // recorremos la lista de pacientes. Usamos la variable global total_patients
    printf("%d\t%-20s\t\t%d\t\t %.0f\n",patientDB[dbs].patient[i].ref,patientDB[dbs].patient[i].ID,patientDB[dbs].patient[i].gps_position.distance,patientDB[dbs].patient[i].gps_position.angle);
  }
  printf("\n\n");
}

/**********************************************************************
Funcion newOrder que crea los pedidos.
***********************************************************************/
void type_DroneDB::newOrder(int dbs){
	int RefPatientSelected; // variable para guardar la selección del usuario
	int io=total_orders; //variable para controlar el total de pedidos. io: index orders
	int id=0; // variable para indice de farmacos. id: index drugs
	int aux; // variable auxiliar para usar cuando se han de repetir los pedidos
	char order_selection = 's'; // variable para controlar la seleccion del usuario en pedidos
	char drug_selection; // variable para controlar la seleccion del usuario en farmacos

	while ( order_selection != 'n' && order_selection != 'N' ){
		if ( total_orders <= 100 ){ // controlamos que los pedidos no sean mayores a 100
      drug_selection = 's'; // por defecto asignamos el caracter s a la variable drug_selection
      io=io+1; // aumentamos el index orders
      ordersDB[dbs].orders[io].ref = io; // asignamos un valor a ref unico a cada pedido
      ordersDB[dbs].orders[io].totalWeight = 0; // ponemos el peso de este nuevo pedido a 0

      printf("\tRef. paciente: "); fflush(stdin); scanf("%d", &RefPatientSelected);
      ordersDB[dbs].orders[io].refPatient = RefPatientSelected;

      printf("\tNumero de envios: "); fflush(stdin); scanf("%d", &ordersDB[dbs].orders[io].numbers);
      if( ordersDB[dbs].orders[io].numbers > 1){ // si el numero de pedidos es mayor a 1 entonces:
        printf("\tNumero de dias entre cada envio (Entre 1 y 15 dias): "); fflush(stdin); scanf("%d", &daysBetweenOrders); //guardamos el numero de dias entre envios en la variable daysBetweenOrders
        printf("\tDia del primer envio: "); fflush(stdin); scanf("%d", &ordersDB[dbs].orders[io].date.day);
        printf("\tMes del primer envio: "); fflush(stdin); scanf("%d", &ordersDB[dbs].orders[io].date.month);
        printf("\tA\xA4o del primer envio: "); fflush(stdin); scanf("%d", &ordersDB[dbs].orders[io].date.year);
      } else { // si el numero de pedidos no es mayor a 1 entonces:
        printf("\tDia del envio: "); fflush(stdin); scanf("%d", &ordersDB[dbs].orders[io].date.day);
        printf("\tMes del envio: "); fflush(stdin); scanf("%d", &ordersDB[dbs].orders[io].date.month);
        printf("\tA\xA4o del envio: "); fflush(stdin); scanf("%d", &ordersDB[dbs].orders[io].date.year);
      }

      while ( drug_selection != 'n' && drug_selection != 'N'){

        if(id < 5){// controlamos que el numero de farmacos por pedido no sea mayor a 5
          fflush(stdin);
          id=id+1; //aumentamos en 1 el index drugs
          printf("\n\n\tNombre del farmaco (Entre 1 y 20 caracteres): "); fflush(stdin); scanf("%[^\n]s", &ordersDB[dbs].orders[io].drugs[id].name);
          /* mostramos al usuario el peso restante en gramos 3000-totalWeight*/
          printf("\tPeso del farmaco (Menor de %d gramos): ", 3000-ordersDB[dbs].orders[io].totalWeight); fflush(stdin); scanf("%d", &ordersDB[dbs].orders[io].drugs[id].weight);
          printf("\tUnidades del farmaco: "); fflush(stdin); scanf("%d", &ordersDB[dbs].orders[io].drugs[id].units);
          fflush(stdin);

          /*calculamos el total del peso de todas las unidades del farmaco*/
          ordersDB[dbs].orders[io].drugs[id].weight=ordersDB[dbs].orders[io].drugs[id].units*ordersDB[dbs].orders[io].drugs[id].weight;

          ordersDB[dbs].orders[io].totalWeight=ordersDB[dbs].orders[io].totalWeight+ordersDB[dbs].orders[io].drugs[id].weight; //almacenamos en totalWeight el peso total de este pedido
          printf("\n\n\tOtro farmaco (S/N)?: "); fflush(stdin); scanf("%c", &drug_selection);
          ordersDB[dbs].orders[io].totalDrugs=id; // guardamos en totalDrugs el numero total de farmacos en este pedido

          if(ordersDB[dbs].orders[io].totalWeight > 3000){ // controlamos que el peso total de los farmacos no super los 3000g por pedido, si es mayor entonces:
            printf("\tERROR: El peso total: %d supera el maximo de 3000g por pedido.\n", ordersDB[dbs].orders[io].totalWeight);
            printf("\tIntroduzca nuevamente los farmacos, sin superar los 3000g maximos.\n");
            drug_selection = 's'; // asignamos el caracter s a la variable drug_selection para reiniciar la inserccion de farmacos
            id=0; // reiniciamos la variable index drugs sobreescribiremos los farmacos ya introducidos
            ordersDB[dbs].orders[io].totalWeight=0; // reiniciamos el peso total de los farmacos en este pedido
          }
          else if (ordersDB[dbs].orders[io].totalWeight == 3000){ // si el peso es exactamente igual a 3000g no dejamos introducir mas farmacos
            printf("\tATENCION: Peso maximo superado. No se permiten mas farmacos en este pedido.\n");
            drug_selection = 'n'; //asignamos el caracter n a la variable drug_selection para continuar con otro pedido
          }

        }else{ // si se intenta introducir un 6to farmaco entonces
          printf("\tERROR: No se permiten más farmacos en este pedido.\n");
          drug_selection = 'n';
        }
      }


      if( ordersDB[dbs].orders[io].numbers > 1){ // si el usuario selecciono un numero de envios mayor a 1 entonces:
        aux=io; // asignamos a la variable aux el contenido de la variable io (index orders) que es justamente el pedido recientemente creado
        /* vamos a iterar hasta que multiorder_io (multiorder_Index Orders) sea menor al numero total de pedidos seleccionados*/
        for(int multiorder_io=1;multiorder_io<ordersDB[dbs].orders[aux].numbers;multiorder_io++){
          io=io+1;
          ordersDB[dbs].orders[io].ref = io; // asignamos a cada pedido un valor ref unico
          ordersDB[dbs].orders[io].refPatient = RefPatientSelected; // asignamos el pedido al usuario original

          /* vamos a usar estos condicionales para llamar a la variable que suma dias a una fecha*/
          if (multiorder_io == 1){ // si el multiorder_indexorders es igual a uno, entonces
            /* le pasamos la fecha del pedido original y le sumamos daysBetweenOrders*/
            sumDays(ordersDB[dbs].orders[aux].date.day, ordersDB[dbs].orders[aux].date.month, ordersDB[dbs].orders[aux].date.year, daysBetweenOrders);
            /* sumDays nos devuelve newDay, newMonth y newYear*/
            ordersDB[dbs].orders[io].date.day = newDay;
            ordersDB[dbs].orders[io].date.month = newMonth;
            ordersDB[dbs].orders[io].date.year = newYear;
          } else { // si el multiorder_indexorders no es 1 entonces
            /* le pasamos la fecha del pedido anterior, esto lo conseguimos restando un 1 a io (index orders)
               y le pasamos nuevamente daysBetweenOrders para que sume esos dias */
            sumDays(ordersDB[dbs].orders[io-1].date.day, ordersDB[dbs].orders[io-1].date.month, ordersDB[dbs].orders[io-1].date.year, daysBetweenOrders);
            ordersDB[dbs].orders[io].date.day = newDay;
            ordersDB[dbs].orders[io].date.month = newMonth;
            ordersDB[dbs].orders[io].date.year = newYear;
          }

          /*vamos a asignar al nuevo pedido los mismos farmacos que el original*/
          for(int multiorder_id = 1;multiorder_id<ordersDB[dbs].orders[aux].totalDrugs+1;multiorder_id++){
            ordersDB[dbs].orders[io].drugs[multiorder_id].units = ordersDB[dbs].orders[aux].drugs[multiorder_id].units;
            strcpy(ordersDB[dbs].orders[io].drugs[multiorder_id].name, ordersDB[dbs].orders[aux].drugs[multiorder_id].name);
            ordersDB[dbs].orders[io].drugs[multiorder_id].weight = ordersDB[dbs].orders[aux].drugs[multiorder_id].weight;
            ordersDB[dbs].orders[io].totalDrugs=ordersDB[dbs].orders[aux].totalDrugs;
          }
          /*asignamos el mismo peso al nuevo pedido que el original*/
          ordersDB[dbs].orders[io].totalWeight=ordersDB[dbs].orders[aux].totalWeight;
        }
      }

      id=0; // reiniciamos id (index drugs) para prepararlo para un nuevo pedido

      printf("\tOtro pedido (S/N)?: "); fflush(stdin); scanf("%c", &order_selection);
      printf("\n\n");
      total_orders=io; // asignamos a total_orders el valor de io (index_orders)
		}
    else { // si se intenta introducir un el pedido numero 101
      printf("ERROR: No se permiten mas pedidos en la base de datos.\n");
      order_selection = 'n';
    }
	}
	printf("Pedidos guardados satisfactoriamente\n");
}

/**********************************************************************
Funcion listOrders que lista los pedidos en las fechas indicadas.
***********************************************************************/
void type_DroneDB::listOrders(int dbs, cString WareDesc){

  fflush(stdin);
  printf("Lista diaria pedidos:\n");
  printf("Dia: "); scanf("%d", &day); fflush(stdin);
  printf("Mes: "); scanf("%d", &month); fflush(stdin);
  printf("A\xA4o: "); scanf("%d", &year); fflush(stdin);
  printf("\n\n");

  for(int io=1;io<total_orders+1;io++){// iteramos sobre el numero total de pedidos
    /* solo mostramos los pedidos que coincidan con la fecha insertada por el usuario */
     if (day == ordersDB[dbs].orders[io].date.day && month == ordersDB[dbs].orders[io].date.month && year == ordersDB[dbs].orders[io].date.year) {
        printf("Pedido %d - Cliente %d - ALMACEN - ", io, ordersDB[dbs].orders[io].refPatient);
        for(int i=0;i<=strlen(WareDesc);i++){printf("%c", toupper(WareDesc[i]));}; printf("\n\n");
        /* obtenemos la distancia y angulo del paciente que ha realizado el pedido */
        printf("Paciente: %d\n", ordersDB[dbs].orders[io].refPatient);
        printf("Ubicacion destino: Distancia: %d y Angulo: %.0f\n", patientDB[dbs].patient[ordersDB[dbs].orders[io].refPatient].gps_position.distance, patientDB[dbs].patient[ordersDB[dbs].orders[io].refPatient].gps_position.angle);
        for(int id = 1;id<ordersDB[dbs].orders[io].totalDrugs+1;id++){ // iteramos sobre el numero total de farmacos de este pedido
          printf("%d Unidades\t%-15s \t Peso: %d gramos\n", ordersDB[dbs].orders[io].drugs[id].units,ordersDB[dbs].orders[io].drugs[id].name,ordersDB[dbs].orders[io].drugs[id].weight);
        }
        printf("\t\tPeso total del envio %d:\t %d gramos\n\n", io, ordersDB[dbs].orders[io].totalWeight);
    }
  }
}

/**********************************************************************
Funcion schDailyOrders que lista los pedidos en las fechas indicadas.
***********************************************************************/
void type_DroneDB::schDailyOrders(int dbs){
  int iso = 0; /*iso: index schedule order */
  int routeId = 0;
  int leftWeight = 0;
  int distanceInThisStop;
  //int ir = 0; /*ir: index routes*/

  fflush(stdin);
  printf("Dia: "); scanf("%d", &day); fflush(stdin);
  printf("Mes: "); scanf("%d", &month); fflush(stdin);
  printf("A\xA4o: "); scanf("%d", &year); fflush(stdin);
  printf("\n\n");

  // programamos los pedidos de este día
  for(int io=1;io<total_orders+1;io++){// iteramos sobre el numero total de pedidos.
    /* solo programamos los pedidos que coincidan con la fecha insertada por el usuario */
     if (day == ordersDB[dbs].orders[io].date.day && month == ordersDB[dbs].orders[io].date.month && year == ordersDB[dbs].orders[io].date.year) {
       schOrdersDB[dbs].schOrders[iso].assigned = false;
       schOrdersDB[dbs].schOrders[iso].code = iso;
       schOrdersDB[dbs].schOrders[iso].patientRef = ordersDB[dbs].orders[io].refPatient;
       schOrdersDB[dbs].schOrders[iso].totalWeight = ordersDB[dbs].orders[io].totalWeight;
       strcpy(schOrdersDB[dbs].schOrders[iso].clientID, patientDB[dbs].patient[ordersDB[dbs].orders[io].refPatient].ID);
       schOrdersDB[dbs].schOrders[iso].angle = patientDB[dbs].patient[ordersDB[dbs].orders[io].refPatient].gps_position.angle;
       schOrdersDB[dbs].schOrders[iso].client_coor_X = patientDB[dbs].patient[ordersDB[dbs].orders[io].refPatient].gps_position.x;
       schOrdersDB[dbs].schOrders[iso].client_coor_Y = patientDB[dbs].patient[ordersDB[dbs].orders[io].refPatient].gps_position.y;
       iso++;
    }
  }

  total_sch_orders = iso;

  //en caso de que se este reprogramando este dia
  for(int crsch = 1;crsch<=total_sch_orders;crsch++){
    schOrdersDB[dbs].schOrders[crsch].assigned = false;
  }
  for(int cr = 0;cr<total_routes;cr++){
    routesDB[dbs].routes[cr].completed = false;
    routesDB[dbs].routes[cr].totalDistance = 0;
    routesDB[dbs].routes[cr].totalStops = 0;
    routesDB[dbs].routes[cr].totalWeight = 0;
  }

  // Creamos las rutas
  // iteramos sobre todos los pedidos ya programados para este día
  for(int ir=0;ir<total_sch_orders;ir++){
      if(schOrdersDB[dbs].schOrders[ir].assigned == false && routesDB[dbs].routes[routeId].completed == false){
        // Hemos creado una nueva ruta, indicamos que el numero de paradas de momento es 0
        routesDB[dbs].routes[routeId].totalStops = 0;
        // pedido programado asignado
        schOrdersDB[dbs].schOrders[ir].assigned = true;
        // añadimos el pedido a la ruta
        routesDB[dbs].routes[routeId].order[routesDB[dbs].routes[routeId].totalStops] = schOrdersDB[dbs].schOrders[ir];
        // aumentamos el numero de paradas totales de la ruta a 1
        routesDB[dbs].routes[routeId].totalStops = 1;
        // asignamos identificador unico a la ruta
        routesDB[dbs].routes[routeId].routeID = routeId;
       // Distancia desde la coordenada origen x:0 y:0 hasta el cliente
        f_distanceBetweenAxis(0,0,schOrdersDB[dbs].schOrders[ir].client_coor_X,schOrdersDB[dbs].schOrders[ir].client_coor_Y);
        // distancia total de la ruta
        routesDB[dbs].routes[routeId].totalDistance = routesDB[dbs].routes[routeId].totalDistance+distanceBetweenAxis;
        routesDB[dbs].routes[routeId].totalWeight = schOrdersDB[dbs].schOrders[ir].totalWeight;

        // añadimos la distancia de esta ruta
        routesDB[dbs].routes[routeId].distanceByStops[routesDB[dbs].routes[routeId].totalStops] = distanceBetweenAxis;
        // recorremos otros pedidos
        for(int counter=ir+1;counter<total_sch_orders;counter++){
          // esta asignado?
          if(schOrdersDB[dbs].schOrders[counter].assigned == false){
            // comprobamos si el pedido entra en la ruta
            if(checkIfCanAddIn(routesDB[dbs].routes[routeId], schOrdersDB[dbs].schOrders[counter], counter) == true ){
              // marcamos el pedido como asignado
              schOrdersDB[dbs].schOrders[counter].assigned = true;
              //agregamos el pedido a la ruta
              routesDB[dbs].routes[routeId].order[routesDB[dbs].routes[routeId].totalStops] = schOrdersDB[dbs].schOrders[counter];
              // calculamos la distancias entre clientes
              f_distanceBetweenAxis(routesDB[dbs].routes[routeId].order[routesDB[dbs].routes[routeId].totalStops-1].client_coor_X,routesDB[dbs].routes[routeId].order[routesDB[dbs].routes[routeId].totalStops-1].client_coor_Y,schOrdersDB[dbs].schOrders[counter].client_coor_X,schOrdersDB[dbs].schOrders[counter].client_coor_Y);

              // distancia total de la ruta, peso total y añadimos un 1 al total de paradas
              routesDB[dbs].routes[routeId].totalDistance = routesDB[dbs].routes[routeId].totalDistance+distanceBetweenAxis;
              routesDB[dbs].routes[routeId].totalWeight = routesDB[dbs].routes[routeId].totalWeight+schOrdersDB[dbs].schOrders[counter].totalWeight;
              routesDB[dbs].routes[routeId].totalStops++;


              routesDB[dbs].routes[routeId].distanceByStops[routesDB[dbs].routes[routeId].totalStops] = distanceBetweenAxis;
            }
          }
        }

    // añadimos la distancia de vuelta al almacen
    f_distanceBetweenAxis(routesDB[dbs].routes[routeId].order[routesDB[dbs].routes[routeId].totalStops-1].client_coor_X,routesDB[dbs].routes[routeId].order[routesDB[dbs].routes[routeId].totalStops-1].client_coor_Y,0,0);
    routesDB[dbs].routes[routeId].totalDistance = routesDB[dbs].routes[routeId].totalDistance+distanceBetweenAxis;

    routesDB[dbs].routes[routeId].distanceByStops[routesDB[dbs].routes[routeId].totalStops+1] = distanceBetweenAxis;

    // marcamos la ruta como completada
    routesDB[dbs].routes[routeId].completed = true;
    // aumentamos el numero id de las rutas
    routeId++;
    total_routes = routeId;
    }
  }

  // imprimimos las rutas
    for(int pri=0;pri<total_routes;pri++){ // print route index
      printf("\tRuta %d \n" , routesDB[dbs].routes[pri].routeID+1);
      // por cada ruta imprimimos sus pedidos
      for( int ptsi=0;ptsi<=routesDB[dbs].routes[pri].totalStops;ptsi++){ // print total stops index
        // obtenemos la distancia a esta parada
        distanceInThisStop = routesDB[dbs].routes[pri].distanceByStops[ptsi+1];
        leftWeight = leftWeight+routesDB[dbs].routes[pri].order[ptsi-1].totalWeight;
        // para imprimir las distancias entre clientes
        if(ptsi == 0){
          // estamos en origen coordenadas x:0, y:0
          printf("Origen a Cliente %d \t-- Distancia recorrida:%d m \t Angulo:%.0f \t Peso:%d g\n", routesDB[dbs].routes[pri].order[ptsi].patientRef, distanceInThisStop, routesDB[dbs].routes[pri].order[ptsi].angle, routesDB[dbs].routes[pri].totalWeight-leftWeight);
        }
        else if (ptsi == routesDB[dbs].routes[pri].totalStops){
          // estamos en el cliente final
          f_angleBetweenPatients(routesDB[dbs].routes[pri].order[ptsi-1].client_coor_X,routesDB[dbs].routes[pri].order[ptsi-1].client_coor_Y,0,0,routesDB[dbs].routes[pri].order[ptsi-1].angle);
          printf("Cliente %d a Origen \t-- Distancia recorrida:%d m \t Angulo:%.0f \t Peso:%d g\n", routesDB[dbs].routes[pri].order[ptsi-1].patientRef, distanceInThisStop, angleBetweenAxis, routesDB[dbs].routes[pri].totalWeight-leftWeight);
        } else {
          // estamos en cualquier otro punto de la ruta
          f_angleBetweenPatients(routesDB[dbs].routes[pri].order[ptsi-1].client_coor_X,routesDB[dbs].routes[pri].order[ptsi-1].client_coor_Y,routesDB[dbs].routes[pri].order[ptsi].client_coor_X,routesDB[dbs].routes[pri].order[ptsi].client_coor_Y,routesDB[dbs].routes[pri].order[ptsi].angle);
          if(distanceInThisStop != 0){
            printf("Cliente %d a Cliente %d \t-- Distancia recorrida:%d m\t Angulo:%.0f \t Peso:%d g\n", routesDB[dbs].routes[pri].order[ptsi-1].patientRef, routesDB[dbs].routes[pri].order[ptsi].patientRef, distanceInThisStop, angleBetweenAxis, routesDB[dbs].routes[pri].totalWeight-leftWeight);
          }
        }
      }
      leftWeight = 0;
      printf("Distancia total de la ruta: %d m\n\n", routesDB[dbs].routes[pri].totalDistance);
    }
}

/**********************************************************************
Funcion initializePatientsDB que inicializa la BBDD de pacientes.
***********************************************************************/
void type_DroneDB::initializePatientsDB(int dbs){
  // Inicializamos la bbdd de pacientes hasta con 10 pacientes por almacen
  int ip=total_patients;
  srand(time(0));
  printf("Almacen: %d - Inicializando BBDD de pacientes", dbs);
  for(int ipdb=1;ipdb<=10;ipdb++){
    printf(".");
    patientDB[dbs].patient[ipdb].ref = ipdb;
    switch(ipdb){
      case 1: strcpy(patientDB[dbs].patient[ipdb].ID, "David Cucalon"); break;
      case 2: strcpy(patientDB[dbs].patient[ipdb].ID, "Pedro Gimenez"); break;
      case 3: strcpy(patientDB[dbs].patient[ipdb].ID, "Aitor Mediavilla"); break;
      case 4: strcpy(patientDB[dbs].patient[ipdb].ID, "Maria Gomez"); break;
      case 5: strcpy(patientDB[dbs].patient[ipdb].ID, "Jesus Martinez"); break;
      case 6: strcpy(patientDB[dbs].patient[ipdb].ID, "Jordi Pujol"); break;
      case 7: strcpy(patientDB[dbs].patient[ipdb].ID, "Mariano Guitierrez"); break;
      case 8: strcpy(patientDB[dbs].patient[ipdb].ID, "Albert Rivera"); break;
      case 9: strcpy(patientDB[dbs].patient[ipdb].ID, "Martina Fernandez"); break;
      case 10: strcpy(patientDB[dbs].patient[ipdb].ID, "Juana de arco"); break;
    }

    patientDB[dbs].patient[ipdb].gps_position.distance = rand()%7500 + 1;
    patientDB[dbs].patient[ipdb].gps_position.angle = rand()%2000 + 1;
    patientDB[dbs].patient[ipdb].gps_position.x = patientDB[dbs].patient[ipdb].gps_position.distance * cos(patientDB[dbs].patient[ipdb].gps_position.angle*3.1415/1000);
    patientDB[dbs].patient[ipdb].gps_position.y = patientDB[dbs].patient[ipdb].gps_position.distance * sin(patientDB[dbs].patient[ipdb].gps_position.angle*3.1415/1000);
    ip++;
    Sleep(10); // esperamos un segundo para que la semilla generadora de numeros aleatorios cambie
  }
  printf("OK\n");
  total_patients=ip;
}

/**********************************************************************
Funcion initializeOrdersDB que inicializa la BBDD de pedidos.
***********************************************************************/
void type_DroneDB::initializeOrdersDB(int dbs){
  //Inicializamos la bbdd de pedidos hasta con 15 pedidos en 4 pacientes
  int io=total_orders;
  srand(time(0));
  printf("Almacen: %d - Inicializando BBDD de pedidos", dbs);
  for(int iodb=1; iodb<=15;iodb++){
    printf(".");
    ordersDB[dbs].orders[iodb].ref = iodb;
    ordersDB[dbs].orders[iodb].refPatient = rand()%10 + 1;
    ordersDB[dbs].orders[iodb].date.day = rand()%5 + 1;
    ordersDB[dbs].orders[iodb].date.month = 1;
    ordersDB[dbs].orders[iodb].date.year = 2021;
    if(iodb>0 && iodb<=5){
      strcpy(ordersDB[dbs].orders[iodb].drugs[1].name, "Pfizer Pack");
    }
    else if (iodb>6 && iodb<=10){
      strcpy(ordersDB[dbs].orders[iodb].drugs[1].name, "Paracetamol");
    }
    else{
      strcpy(ordersDB[dbs].orders[iodb].drugs[1].name, "Ibuprofeno");
    }
    ordersDB[dbs].orders[iodb].drugs[1].weight = 150;
    ordersDB[dbs].orders[iodb].drugs[1].units = rand()%5 + 1;
    ordersDB[dbs].orders[iodb].drugs[1].weight=ordersDB[dbs].orders[iodb].drugs[1].units*ordersDB[dbs].orders[iodb].drugs[1].weight;
    ordersDB[dbs].orders[iodb].totalWeight=ordersDB[dbs].orders[iodb].drugs[1].weight;
    ordersDB[dbs].orders[iodb].totalDrugs = 1;
    io++;
    Sleep(60); // esperamos unas milesimas de segundo para que la semilla generadora de numeros aleatorios cambie
  }
  printf("OK\n");
  total_orders=io;
}

/**********************************************************************
Funcion listInitializedOrders que lista los pedidos que han sido
generados automaticamente.
***********************************************************************/
void type_DroneDB::listInitializedOrders(int dbs){
  printf("Cliente\tFecha\t\tFarmaco\t\tPeso\tUnidades\n\n");
  for(int io=1;io<total_orders+1;io++){
      for(int id = 1;id<ordersDB[dbs].orders[io].totalDrugs+1;id++){
        printf("%d \t %d/%d/%d \t %s \t %d \t %d \n", ordersDB[dbs].orders[io].refPatient, ordersDB[dbs].orders[io].date.day, ordersDB[dbs].orders[io].date.month, ordersDB[dbs].orders[io].date.year, ordersDB[dbs].orders[io].drugs[id].name, ordersDB[dbs].orders[io].drugs[id].weight, ordersDB[dbs].orders[io].drugs[id].units);
      }
  }
  printf("\n\n");
}

/**********************************************************************
Funcion deleteData que elimina datos asociados a un almacen en
concreto. Esta función se usa cuando existen más de 10 almacenes.
***********************************************************************/
void type_DroneDB::deleteData(int dbs){

  for(int crsch = 1;crsch<=total_sch_orders;crsch++){
    schOrdersDB[dbs].schOrders[crsch].assigned = false;
  }
  for(int cr = 0;cr<total_routes;cr++){
    routesDB[dbs].routes[cr].completed = false;
    routesDB[dbs].routes[cr].totalDistance = 0;
    routesDB[dbs].routes[cr].totalStops = 0;
    routesDB[dbs].routes[cr].totalWeight = 0;
  }

  total_patients = 0;
  total_orders= 0;
  total_sch_orders = 0;
  total_routes = 0;
}
