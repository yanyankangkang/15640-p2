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
    RMIMessageInvoke.java \
    SocketCache.java \
    RMINaming.java \
	RMIMessageLookup.java \
	CommModuleClient.java \
	CommModuleServer.java \
	RMIRegistry.java \
	RMIMessageReg.java \
	TestClient.java \
	TestRemoteObjectInterface_stub.java \
    Server.java

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
	java -cp "../bin" Server

runcli:
	java -cp "../bin" TestClient

runall:
	@echo "starting registry on port 1099..."
	java -cp "../bin" RMIRegistry &
	sleep 1
	@echo "starting server on port 15640..."
	java -cp "../bin" Server &
	sleep 1
	@echo "starting test client..."
	@echo "looking for registry on localhost port 1099..."
	java -cp "../bin" TestClient

