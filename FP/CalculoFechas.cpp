/******************************************************************************
* Modulo que realiza los calculos con las fechas pasadas por parametro.
* Suma un numero de días X a una fecha Y y lo devuelve por las variables
* David L. Version 1.0 - 04/01/2021
******************************************************************************/
#include "CalculoFechas.h"
#include <stdio.h>
/*******************************************************************************
Declaracion de variables globales
*******************************************************************************/
int month, year;
int newDay,newMonth,newYear;
int day=1;

/*******************************************************************************
Funcion que dice si un año es bisiesto o no.
*******************************************************************************/
bool bisiesto(int y){
  if ((y%4==0 && y%100!=0) || y%400==0){
    return true;
  }else {
    return false;
  }
}

/*******************************************************************************
Funcion para sumar días a una fecha
*******************************************************************************/
void sumDays(int d, int m, int y, int daysToSum){
  const int daysPerMonth[12] = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
  for (int i=0;i<daysToSum;i++)
   {
       d++;
       if (d > daysPerMonth[m-1] || ( m==2 && d==29 && !bisiesto(y) ) )
       {
           d = 1;
           m++;
           if (m==13)
           {
               m = 1;
               y++;
           }
      }
  }
  newDay=d;
  newMonth=m;
  newYear=y;
}
