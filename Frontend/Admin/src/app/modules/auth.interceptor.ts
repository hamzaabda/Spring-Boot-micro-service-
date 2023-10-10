import { Injectable } from '@angular/core';
import {
  HttpInterceptor,
  HttpRequest,
  HttpHandler,
  HttpEvent,
} from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {
  intercept(
    req: HttpRequest<any>,
    next: HttpHandler
  ): Observable<HttpEvent<any>> {

    // Check if the request URL ends with '/login' or '/register'
    if (req.url.endsWith('/login') || req.url.endsWith('/register') || req.url.endsWith('/google') || req.url.endsWith('/facebook')  || req.url.endsWith('/getroles'))  {
      // Do not add the Authorization header for Login or Register requests
      return next.handle(req);
    }

    // For all other requests, add the Authorization header with the access token
    const token = localStorage.getItem('access_token'); 

    const authReq = req.clone({
      setHeaders: {
        Authorization: `Bearer ${token}`,
      },
    });

    return next.handle(authReq);
  }
}
