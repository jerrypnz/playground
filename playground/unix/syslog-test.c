#include <stdio.h>
#include <syslog.h>

int main(int argc, const char *argv[]) {
    setlogmask (LOG_UPTO(LOG_NOTICE));
    openlog("syslog-test", LOG_CONS | LOG_PID | LOG_NDELAY, LOG_LOCAL1);
    syslog (LOG_NOTICE, "Program started by User %d", getuid ());
    syslog (LOG_INFO, "A tree falls in a forest");
    return 0;
}
