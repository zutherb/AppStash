sudo update-alternatives --config java

service:jmx:rmi:///jndi/rmi://localhost:10001/jmxrmi
service:jmx:rmi:///jndi/rmi://10.211.55.105:10001/jmxrmi

ProcessId: 10482@java-node
Code Cache            : [...                                               ]
Metaspace             : [                                                  ]
Compressed Class Space: [.                                                 ]
Eden Space            : [...................................................]
Survivor Space        : [..................................................]
Tenured Gen           : [..................................................]
java.lang.OutOfMemoryError: Java heap space
	at groovyjarjarasm.asm.ByteVector.<init>(Unknown Source)
	at groovyjarjarasm.asm.ClassWriter.toByteArray(Unknown Source)
	at org.codehaus.groovy.control.CompilationUnit$15.call(CompilationUnit.java:796)
	at org.codehaus.groovy.control.CompilationUnit.applyToPrimaryClassNodes(CompilationUnit.java:1036)
	at org.codehaus.groovy.control.CompilationUnit.doPhaseOperation(CompilationUnit.java:572)
	at org.codehaus.groovy.control.CompilationUnit.processPhaseOperations(CompilationUnit.java:550)
	at org.codehaus.groovy.control.CompilationUnit.compile(CompilationUnit.java:527)
	at groovy.lang.GroovyClassLoader.doParseClass(GroovyClassLoader.java:279)
	at groovy.lang.GroovyClassLoader.parseClass(GroovyClassLoader.java:258)
	at groovy.lang.GroovyShell.parseClass(GroovyShell.java:613)
	at groovy.lang.GroovyShell.parse(GroovyShell.java:625)
	at groovy.lang.GroovyShell.evaluate(GroovyShell.java:516)
	at groovy.lang.GroovyShell.evaluate(GroovyShell.java:556)
	at groovy.lang.GroovyShell.evaluate(GroovyShell.java:527)
	at io.github.appstash.memoryleak.simulator.GroovyClassloadingMemoryLeakSimulator.doLeakSimulation(GroovyClassloadingMemoryLeakSimulator.java:13)
	at io.github.appstash.memoryleak.simulator.AbstractMemoryLeakSimulator.run(AbstractMemoryLeakSimulator.java:16)
	at java.util.concurrent.Executors$RunnableAdapter.call(Executors.java:511)
	at java.util.concurrent.FutureTask.run(FutureTask.java:266)
	at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1142)
	at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:617)
	at java.lang.Thread.run(Thread.java:745)
	
ProcessId: 10906@java-node
Code Cache    : [.....                                             ]
Eden Space    : [.                                                 ]
Survivor Space: [.                                                 ]
Tenured Gen   : [.......................................           ]
Perm Gen      : [..................................................]
java.lang.OutOfMemoryError: PermGen space
	at java.lang.ClassLoader.defineClass1(Native Method)
	at java.lang.ClassLoader.defineClass(ClassLoader.java:800)
	at java.security.SecureClassLoader.defineClass(SecureClassLoader.java:142)
	at groovy.lang.GroovyClassLoader.access$300(GroovyClassLoader.java:55)
	at groovy.lang.GroovyClassLoader$ClassCollector.createClass(GroovyClassLoader.java:471)
	at groovy.lang.GroovyClassLoader$ClassCollector.onClassNode(GroovyClassLoader.java:488)
	at groovy.lang.GroovyClassLoader$ClassCollector.call(GroovyClassLoader.java:492)
	at org.codehaus.groovy.control.CompilationUnit$15.call(CompilationUnit.java:803)
	at org.codehaus.groovy.control.CompilationUnit.applyToPrimaryClassNodes(CompilationUnit.java:1036)
	at org.codehaus.groovy.control.CompilationUnit.doPhaseOperation(CompilationUnit.java:572)
	at org.codehaus.groovy.control.CompilationUnit.processPhaseOperations(CompilationUnit.java:550)
	at org.codehaus.groovy.control.CompilationUnit.compile(CompilationUnit.java:527)
	at groovy.lang.GroovyClassLoader.doParseClass(GroovyClassLoader.java:279)
	at groovy.lang.GroovyClassLoader.parseClass(GroovyClassLoader.java:258)
	at groovy.lang.GroovyShell.parseClass(GroovyShell.java:613)
	at groovy.lang.GroovyShell.parse(GroovyShell.java:625)
	at groovy.lang.GroovyShell.evaluate(GroovyShell.java:516)
	at groovy.lang.GroovyShell.evaluate(GroovyShell.java:556)
	at groovy.lang.GroovyShell.evaluate(GroovyShell.java:527)
	at io.github.appstash.memoryleak.simulator.GroovyClassloadingMemoryLeakSimulator.doLeakSimulation(GroovyClassloadingMemoryLeakSimulator.java:13)
	at io.github.appstash.memoryleak.simulator.AbstractMemoryLeakSimulator.run(AbstractMemoryLeakSimulator.java:16)
	at java.util.concurrent.Executors$RunnableAdapter.call(Executors.java:471)
	at java.util.concurrent.FutureTask.run(FutureTask.java:262)
	at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1145)
	at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:615)
	at java.lang.Thread.run(Thread.java:744)