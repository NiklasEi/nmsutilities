@echo off

set versions=1.8 1.8.3 1.8.8 1.9.2 1.9.4 1.10.2 1.11.2 1.12.2 1.13 1.13.1 1.14.1 1.15 1.16.1 1.16.3 1.16.5

(for %%a in (%versions%) do (
	java -jar BuildTools.jar --rev %%a --remapped
	java -jar BuildTools.jar --rev %%a --compile craftbukkit
))

pause