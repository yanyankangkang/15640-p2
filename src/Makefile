JFLAGS = -d ../bin
JC = javac
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \
	MyRemote.java \
	MyRemoteException.java \
	TestRemoteObjectInterface.java \
	TestRemoteObject.java \
	RemoteObjectReference.java \
	RMIMessage.java \
	RMIMessageLookup.java \
	CommModuleClient.java \
	CommModuleServer.java \
	RMIRegistry.java \
	RMIMessageReg.java \
	TestClient.java \
	TestRemoteObjectInterface_stub.java

default:
	@echo "make build: build project"
	@echo "make clean: clear .class files"
	@echo "make runreg: run registry"
	@echo "make runser: run server"
	@echo "make runcli: run client" 

build: classes

classes: $(CLASSES:.java=.class)

clean:
	$(RM) ..bin/*.class

runreg:
	java -cp "../bin" RMIRegistry

runser:
	java -cp "../bin" CommModuleServer

runcli:
	java -cp "../bin" TestClient