import {Injectable} from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor
} from '@angular/common/http';
import {Observable} from 'rxjs';
import {SecurityService} from "./security.service";

@Injectable()
export class AuthHeaderInterceptor implements HttpInterceptor {

  constructor(private securityService: SecurityService) {
  }

  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    const req = request.clone({
      setHeaders: {
        'Content-Type': 'application/json',
        Accept: 'application/json',
        Authorization: 'Bearer ' + this.securityService.getToken()
      }
    });
    return next.handle(req);
  }
}
