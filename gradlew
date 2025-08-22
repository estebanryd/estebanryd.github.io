#!/usr/bin/env sh

##############################################################################
##
##  Gradle start up script for UN*X
##
##############################################################################

# Add default JVM options here. You can also use JAVA_OPTS and GRADLE_OPTS to pass JVM options to this script.
DEFAULT_JVM_OPTS="-Xmx64m -Xms64m"

APP_BASE_NAME=$(basename "$0")
APP_HOME=$(cd "$(dirname "$0")"; pwd -P)

# Resolve symlinks
while [ -h "$APP_HOME/$APP_BASE_NAME" ]; do
  ls=$(ls -ld "$APP_HOME/$APP_BASE_NAME")
  link=$(expr "$ls" : '.*-> \(.*\)$')
  if expr "$link" : '/.*' > /dev/null; then
    APP_HOME=$link
  else
    APP_HOME=$(dirname "$APP_HOME/$link")
  fi
  APP_BASE_NAME=$(basename "$APP_HOME/$link")
  APP_HOME=$(cd "$APP_HOME"; pwd -P)
done

CLASSPATH=$APP_HOME/gradle/wrapper/gradle-wrapper.jar

# Determine the Java command to use to start the JVM.
if [ -n "$JAVA_HOME" ] ; then
  if [ -x "$JAVA_HOME/jre/sh/java" ] ; then
    # IBM's JDK on AIX uses strange locations for the executables
    JAVACMD="$JAVA_HOME/jre/sh/java"
  else
    JAVACMD="$JAVA_HOME/bin/java"
  fi
  if [ ! -x "$JAVACMD" ] ; then
    echo "ERROR: JAVA_HOME is set to an invalid directory: $JAVA_HOME" >&2
    echo "Please set the JAVA_HOME variable in your environment to match the location of your Java installation." >&2
    exit 1
  fi
else
  JAVACMD="java"
  which java >/dev/null 2>&1 || {
    echo "ERROR: JAVA_HOME is not set and no 'java' command could be found in your PATH." >&2
    echo "Please set the JAVA_HOME variable in your environment to match the location of your Java installation." >&2
    exit 1
  }
fi

# Increase the maximum file descriptors if we can.
MAX_FD="maximum"
if [ "$cygwin" = "true" -o "$darwin" = "true" ] ; then
  ulimit -n 1024 > /dev/null 2>&1
else
  ulimit -n "$MAX_FD" > /dev/null 2>&1
fi

# For Cygwin, ensure paths are in UNIX format before anything is touched.
if [ "$cygwin" = "true" ]; then
  APP_HOME=$(cygpath --path --unix "$APP_HOME")
  JAVACMD=$(cygpath --unix "$JAVACMD")
fi

# For MacOS, determine if we are running on 10.5 or above.
if [ "$darwin" = "true" ]; then
  GRADLE_OPTS="$GRADLE_OPTS -Dorg.gradle.appname=$APP_BASE_NAME"
fi

# Split up the JVM_OPTS and GRADLE_OPTS values into an array, following shell quoting and substitution rules
function splitJvmOpts() {
  JVM_OPTS=($1)
}

# Escape application args
save () {
  for i do printf %s\\n "$i" | sed "s/'/'\\\\''/g;1s/^/'/;
    $s/$/' \\ \\ /" ; done
  echo " "
}
APP_ARGS=$(save "$@")

# Collect all arguments for the java command, following the shell quoting and substitution rules
eval set -- $DEFAULT_JVM_OPTS $JAVA_OPTS $GRADLE_OPTS -classpath "$CLASSPATH" org.gradle.wrapper.GradleWrapperMain "$APP_ARGS"

exec "$JAVACMD" "$@"