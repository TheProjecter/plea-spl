#! /bin/sh
# $Id: build.sh 11368 2006-10-31 17:01:11Z tfmorris $
#

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
ANT_HOME=`pwd`/../tools/apache-ant-1.6.5

echo ANT_HOME is: $ANT_HOME
echo
echo Starting Ant...
echo

$ANT_HOME/bin/ant $*

#exit
