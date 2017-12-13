@echo off
cd /d %~dp0..
echo db-migration-assistant(java, Oracle)
@title db-migration-assistant(java, Oracle)
setLocal EnableDelayedExpansion
set CLASSPATH="conf
for /f "tokens=* delims=" %%a in ('dir "*.jar" /b') do (
   set CLASSPATH=!CLASSPATH!;%%a
)
set CLASSPATH=!CLASSPATH!"
echo %CLASSPATH%
java -Xmx512m -Xmn256m -Djava.ext.dirs=lib -Dlog4j.configuration=log4j.properties -cp %CLASSPATH% ren.ashin.db.migration.assistant.MigrationAssistant