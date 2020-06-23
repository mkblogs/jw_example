import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import {  AppUserAuth, ResponseDTO, Error } from 'src/app/model/model';
import { UserService } from '../user/user.service';
import { Router } from '@angular/router';


@Injectable({ providedIn: 'root' })
/* tslint:disable */
export class AuthenticationService {
    private currentUserSubject: BehaviorSubject<AppUserAuth>;
    public currentUser: Observable<AppUserAuth>;

     responseObject : ResponseDTO<AppUserAuth,Error> = null;
    

    constructor(private userService: UserService, private router: Router) {
        this.currentUserSubject = new BehaviorSubject<AppUserAuth>(JSON.parse(localStorage.getItem('currentUser')));
        this.currentUser = this.currentUserSubject.asObservable();
    }

    public get currentUserValue(): AppUserAuth {
        return this.currentUserSubject.value;
    }

    login(loginName: string, password: string) {
        this.userService.login(loginName,password).subscribe(data => {
            this.responseObject = JSON.parse(JSON.stringify(data));           
            const isError = this.responseObject.isError;
            if(isError){
                var errorObject: Error = JSON.parse(JSON.stringify(this.responseObject.errorObject));
                this.constructErrorObject(errorObject);
            }else{
                let user : AppUserAuth =  this.responseObject.successObject;
                if(user.isAuthenticated){
                    localStorage.setItem('currentUser', JSON.stringify(user));
                    this.currentUserSubject.next(user);
                    this.router.navigate(['/welcome']);
                }else{
                    alert("Login Failed");
                }                
            }
        },
         error => {
            alert(error);
         }
        );
    }

  constructErrorObject (errorObject: Error ) {
     alert('error ' + errorObject.errorMsg);   
  }
  
    logout() {
        // remove user from local storage to log user out
        localStorage.removeItem('currentUser');
        this.currentUserSubject.next(null);
    }
}