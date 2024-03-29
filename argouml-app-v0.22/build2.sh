#! /bin/sh
# $Id: build2.sh 9985 2006-03-07 19:00:38Z tfmorris $
#

# This variant of the build is configured for the Eclipse project directories

# OS specific support.
darwin=false

case "`uname`" in
		Darwin*) darwin=true;;
esac

# +-------------------------------------------------------------------------+
# | Verify and Set Required Environment Variables                           |
# +-------------------------------------------------------------------------+
if [ "$JAVA_HOME" = "" ] ; then
	if $darwin; then
		# Set Java Home automatically
        JAVA_HOME=/Library/Java/Home
		export JAVA_HOME
	else
		echo "***************************************************************"
		echo "  ERROR: JAVA_HOME environment variable not found."
		echo ""
		echo "  Please set JAVA_HOME to the Java JDK installation directory."
		echo "***************************************************************"
		exit 1
	fi
fi

#
# build.sh always calls the version of ant distributed with ArgoUML
#
ANT_HOME=`pwd`/../argouml-core-tools/ant-1.6.2

echo ANT_HOME is: $ANT_HOME
echo
echo Starting Ant...
echo

$ANT_HOME/bin/ant -Doverride.build.properties=eclipse-ant-build.properties $*

#exit
