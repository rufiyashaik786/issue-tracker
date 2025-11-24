import { HttpInterceptorFn, HttpErrorResponse } from '@angular/common/http';
import { inject } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { catchError, throwError } from 'rxjs';

export const errorInterceptor: HttpInterceptorFn = (req, next) => {
  const toastr = inject(ToastrService);

  return next(req).pipe(
    catchError((error: HttpErrorResponse) => {
      // network / backend unreachable
      if (error.status === 0) {
        toastr.error('Backend server unreachable.');
      }

      // validation error
      else if (error.status === 400) {
        toastr.error('Validation failed.');
      }

      // not found
      else if (error.status === 404) {
        toastr.warning('Resource not found.');
      }

      // internal server error
      else if (error.status === 500) {
        toastr.error('Server error.');
      }

      // default
      else {
        toastr.error(error.error?.message || 'Unexpected error occurred.');
      }

      return throwError(() => error);
    })
  );
};
