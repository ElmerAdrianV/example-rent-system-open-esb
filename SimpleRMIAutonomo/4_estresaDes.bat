echo off
echo ejecuta el estresador para sembrar los clientes
echo uso:
echo 4_estresa NumCltes NumSolicitudesPorClte HOSTNAME (en caso de omitirlo se usa localhost, si se omiten los clientes usa 15 y el localhost)
echo on

rem ====================================================
rem 	definición de roles de jars para ejecucion
rem ====================================================

set cb=%cd%\tstRMI_desacoplado.jar
set JAR_INTERFAZ=InterfazServicioEstres.jar
set JAR_CLTE_ESTRESS=POJO_WS_Customer_Vehicle.jar
set POJO_ESTRESS=Clte_des

rem ====================================================
rem 	
rem ====================================================

set clpt=%cb%;%JAR_INTERFAZ%;%JAR_CLTE_ESTRESS%

if [%1] NEQ [] goto conclientes
estresador 10 -Djava.rmi.server.codebase=file:%cb% -cp %clpt% example.hello.Distribuidor %POJO_ESTRESS% localhost 200
goto fin

:conclientes

if [%2] NEQ [] goto consolicitudes
estresador %1 -Djava.rmi.server.codebase=file:%cb% -cp %clpt% example.hello.Distribuidor %POJO_ESTRESS% localhost 200
goto fin

:consolicitudes
if [%3] NEQ [] goto conHost
estresador %1 -Djava.rmi.server.codebase=file:%cb% -cp %clpt% example.hello.Distribuidor %POJO_ESTRESS% localhost %2
goto fin

:conHost
estresador %1 -Djava.rmi.server.codebase=file:///%cb% -cp %clpt% example.hello.Distribuidor %POJO_ESTRESS% %3 %2


:fin