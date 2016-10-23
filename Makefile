#Makefile
CORE = readernotes/src/core
UI	= readernotes/src/ui
BASE = readernotes
SRC = readernotes/src
EXCEPTIONS = readernotes/src/exceptions
DATABASE = readernotes/src/data
CORE_TESTS = readernotes/test/core
JUNIT_PATH=libs/junit-4.12.jar
HAMCREST=libs/java-hamcrest-2.0.0.0.jar
JDOM=libs/jdom-2.0.6/jdom-2.0.6.jar
JUNIT_EXEC=org.junit.runner.JUnitCore
BUILD=readernotes-0.3.0
BINARY=readernotes-0.3.0.deb

all:
	javac -cp $(JUNIT_PATH):$(JDOM) `find $(CORE) $(EXCEPTIONS) $(CORE_TESTS) $(DATABASE) $(BASE) $(UI) \
    -name *.java`

run:
#After loading the jdom library it needs to go back to the base directory.
	java -classpath libs/jdom-2.0.6/jdom-2.0.6.jar: readernotes.Shell

#This is how to run junit from the jar package inside the project.
test:
	java -cp .:$(HAMCREST):$(JUNIT_PATH) $(JUNIT_EXEC) readernotes.test.core.BookTest
	java -cp .:$(HAMCREST):$(JUNIT_PATH) $(JUNIT_EXEC) readernotes.test.core.ReadingFileTest
	java -cp .:$(HAMCREST):$(JUNIT_PATH) $(JUNIT_EXEC) readernotes.test.core.LibraryTest

package-jar:
	jar cvfm readernotes.jar manifest.txt `find $(CORE) $(EXCEPTIONS) $(CORE_TESTS) $(DATABASE) $(BASE) $(UI) -name *.class`

package:
	jar cvfm readernotes.jar manifest.txt `find $(CORE) $(EXCEPTIONS) $(CORE_TESTS) $(DATABASE) $(BASE) $(UI) -name *.class`
	echo "Building projecto structure"
	mkdir $(BUILD)
	mkdir $(BUILD)/usr
	mkdir $(BUILD)/usr/bin
	mkdir $(BUILD)/usr/bin/readernotes
	mkdir $(BUILD)/usr/bin/readernotes/libs
	mkdir $(BUILD)/usr/bin/readernotes/libs/jdom-2.0.6
	mkdir $(BUILD)/usr/share
	mkdir $(BUILD)/usr/share/applications
	mkdir $(BUILD)/usr/share/pixmaps
	mkdir $(BUILD)/DEBIAN
	mv readernotes.jar $(BUILD)/usr/bin/readernotes/
	cp resources/readernotes.desktop $(BUILD)/usr/share/applications/
	cp resources/readernotes_logo.png $(BUILD)/usr/share/pixmaps/
	cp libs/jdom-2.0.6/jdom-2.0.6.jar $(BUILD)/usr/bin/readernotes/libs/jdom-2.0.6/
	cp resources/control $(BUILD)/DEBIAN/
	dpkg-deb --build $(BUILD)
	rm -r $(BUILD)
	mv $(BINARY) /home/$(USER)/Desktop
clean:
	rm readernotes/src/core/*.class
	rm readernotes/src/exceptions/*.class
	rm readernotes/src/data/*.class
	rm readernotes/src/ui/*.class
	rm readernotes/src/ui/listeners/*.class
	rm readernotes/test/core/*.class
	rm readernotes/Shell.class
