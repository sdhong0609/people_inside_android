package com.beside153.peopleinside.common.exception

import com.beside153.peopleinside.model.common.ErrorEnvelope
import java.io.IOException

class ApiException(val error: ErrorEnvelope) : IOException(error.message)
