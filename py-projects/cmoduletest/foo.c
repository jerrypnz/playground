#include <Python.h>

static PyObject *say_hello(PyObject *self, PyObject *args) {
    const char *name = NULL;
    char *null_ptr = NULL;
    if (!PyArg_ParseTuple(args, "s", &name)) {
        PyErr_SetString(PyExc_ValueError, "expect a string argument");
        return NULL;
    }
    printf("Say hello to you from foo C module, %s\n", name);
    printf("[WARNING] About to explode...");
    *null_ptr = '\n';
    return Py_None;
}


static PyMethodDef FooMethods[] = {
    {"hello", say_hello, METH_VARARGS, "Say hello from a C module"},
    {NULL, NULL, 0, NULL}
};


PyMODINIT_FUNC initfoo(void) {
    fprintf(stderr, "[DEBUG] foo C module initialized\n");
    (void) Py_InitModule("foo", FooMethods);
}
