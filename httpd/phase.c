#include "server.h"
#include "phase.h"
#include "common.h"

static int phase_init_request(server_t *server, connection_t *conn);
static int phase_parse_request(server_t *server, connection_t *conn);
static int phase_find_location(server_t *server, connection_t *conn);
static int phase_handle_request(server_t *server, connection_t *conn);
static int phase_write_response(server_t *server, connection_t *conn);
static int phase_handle_error(server_t *server, connection_t *conn);
static int phase_clean_up(server_t *server, connection_t *conn);

phase_t  std_phases[] = {
/*
 * -- Phase handler func ---------------- Next Phase ---------------
 */
    { NULL,                               PHASE_NULL },     // NULL phase
    { phase_init_request,                 PHASE_PARSE },    // Idle phase
    { phase_parse_request,                PHASE_FIND_LOC }, // Parsing HTTP phase
    { phase_find_location,                PHASE_HANDLE },   // Find location phase
    { phase_handle_request,               PHASE_WRITE },    // Handle phase
    { phase_handle_error,                 PHASE_WRITE },    // Error handling phase
    { phase_write_response,               PHASE_CLEANUP },  // Writing phase
    { phase_clean_up,                     PHASE_NULL }      // Cleanup phase, indicating the end of all phases
};


static int phase_init_request(server_t *server, connection_t *conn) { return STATUS_COMPLETE; }
static int phase_parse_request(server_t *server, connection_t *conn) { return STATUS_COMPLETE; }
static int phase_find_location(server_t *server, connection_t *conn) { return STATUS_COMPLETE; }
static int phase_handle_request(server_t *server, connection_t *conn) { return STATUS_COMPLETE; }
static int phase_write_response(server_t *server, connection_t *conn) { return STATUS_COMPLETE; }
static int phase_handle_error(server_t *server, connection_t *conn) { return STATUS_COMPLETE; }
static int phase_clean_up(server_t *server, connection_t *conn) { return STATUS_COMPLETE; }
