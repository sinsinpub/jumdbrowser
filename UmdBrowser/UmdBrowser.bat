@echo off
set PATH=lib;lib\windows-x86\;%PATH%;

if "%JAVA_HOME%XXX"=="XXX" goto PFLOC
if not exist "%JAVA_HOME%\jre\bin" goto JAVAMISSING
echo Running UmdBrowser...
"%JAVA_HOME%\jre\bin\java" -Xmx512m -Djava.library.path=lib/windows-x86 -jar bin/umdbrowser.jar
goto END

:PFLOC
if "%programfiles(x86)%XXX"=="XXX" goto JAVA32
if not exist "%programfiles(x86)%\Java\jre6\bin" goto JAVAMISSING
echo Running UmdBrowser...
"%programfiles(x86)%\Java\jre6\bin\java" -Xmx512m -Djava.library.path=lib/windows-x86 -jar bin/umdbrowser.jar
goto END

:JAVA32
if not exist "%programfiles%\Java\jre6\bin" goto JAVAMISSING
echo Running UmdBrowser...
"%programfiles%\Java\jre6\bin\java" -Xmx512m -Djava.library.path=lib/windows-x86 -jar bin/umdbrowser.jar
goto END

:JAVAMISSING
echo The required version of Java has not been installed or isn't recognized.
echo Go to http://java.sun.com to install the 32bit Java JRE.
pause

:END