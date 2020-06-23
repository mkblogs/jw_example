import { Injectable } from '@angular/core';
import { HttpInterceptor, HttpRequest, HttpHandler } from '@angular/common/http';
import { AppUserAuth } from '../model/model';
import { AuthenticationService } from '../services/auth/authentication.service';
import { Router } from '@angular/router';

@Injectable()
/* tslint:disable */
export class AuthInterceptor implements HttpInterceptor {
  
  user:  AppUserAuth = null;
   constructor(private authService: AuthenticationService,private router: Router) {
    }
  
  intercept(req: HttpRequest<any>, next: HttpHandler) {
    this.user = this.authService.currentUserValue;
    if (localStorage.getItem('currentUser')) {
      const authorizationData = 'Bearer ' +  this.user.token;
      req = req.clone({
        setHeaders: {
          "Authorization": authorizationData
        }
      });      
	  }	
   return next.handle(req);
  }
}
