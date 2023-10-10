import { Component, OnInit } from '@angular/core';
import { AbstractControl, FormBuilder, FormGroup, ValidatorFn, Validators } from '@angular/forms';

import { AuthenticationService } from '../../../core/services/auth.service';
import { AuthfakeauthenticationService } from '../../../core/services/authfake.service';

import { ActivatedRoute, Router } from '@angular/router';
import { first } from 'rxjs/operators';
import {Title} from "@angular/platform-browser";
import {CookieService} from 'ngx-cookie-service';

import { environment } from '../../../../environments/environment';
import { AuthService } from '../service/auth.service';
import { SocialAuthService } from "angularx-social-login";
import { FacebookLoginProvider,GoogleLoginProvider } from "angularx-social-login";
import { SocialUser } from "angularx-social-login";
import { ChangeDetectorRef } from '@angular/core';

import Swal from "sweetalert2"
import { SocialService } from '../service/social.service';
@Component({
  selector: 'app-signin',
  templateUrl: './signin.component.html',
  styleUrls: ['./signin.component.scss']
})
export class SigninComponent implements OnInit {

  capchaKey = environment.capchaKey;

  loginForm: FormGroup;
  submitted = false;
  error = '';
  returnUrl: string;
  showPassword = false;
  showConfirmPassword = false;
  passwordControl: AbstractControl;
  // set the currenr year
  year: number = new Date().getFullYear();

  
  user: SocialUser;
  loggedIn: boolean;


  // tslint:disable-next-line: max-line-length

  constructor(private formBuilder: FormBuilder, private route: ActivatedRoute, private router: Router, private authenticationService: AuthenticationService,
    private authFackservice: AuthfakeauthenticationService, 
    private authservice:AuthService,
    private titleService:Title, private SocialAuthService:SocialAuthService,private social:SocialService,
    private cdr : ChangeDetectorRef,private cookieService: CookieService

    ) {

      this.titleService.setTitle("CulTechConnect |  Login");
     }

    ngOnInit() {

      this.SocialAuthService.authState.subscribe(( user) => {
        this.user = user;
        this.loggedIn = (user != null);
      });



      this.loginForm = this.formBuilder.group({
        email: ['', [Validators.required,Validators.email]],
        password: ['', [Validators.required]],
        recaptcha: ['', [Validators.required , this.recaptchaValidator()]]
      });

      this.passwordControl = this.loginForm.get('password');
      
  
      // reset login status
      // this.authenticationService.logout();
      // get return url from route parameters or default to '/'
      // tslint:disable-next-line: no-string-literal
      this.returnUrl = this.route.snapshot.queryParams['returnUrl'] || '/';
    }

      // convenience getter for easy access to form fields
  get f() { return this.loginForm.controls; }

  /**
   * Form submit
   */
  onSubmit() {
    this.submitted = true;

    // stop here if form is invalid
    if (this.loginForm.invalid) {
      return;
    } 
    else {
    
        this.authservice.login(this.loginForm.value)
          .subscribe(
            (data) => {
              

              if(data.successmessage === "Authentification Successful")
              {

                localStorage.setItem('access_token',data.tokens.access_token)
                localStorage.setItem('refresh_token',data.tokens.refresh_token)
  
                setTimeout(() => {
                  Swal.fire({
                      title: "Success!",
                      text: "Authentification Reussite",
                      icon: "success",
                      timer: 3000, 
                      showConfirmButton: false
                  });
        
                  this.router.navigate(['/dashboard']);
              }, 3000);
             }

             if(data.errormessage === "Confirm Email Before Login")
             {
              this.error = "Compte desactivé , Confirmer votre email pour continuer ou contacter un administrateur"
             }

            if(data.errormessage === "User With this Email Not found")
            {
              this.error = "Utilisateur avec ce email n existe pas"
            }

           if(data.errormessage === "Invalid credentials")
           {
            this.error = "Mot de passe invalide"
           }

            },
            
            error => {
       
              this.error = error ? error : '';
            });

            
    }
  }

  togglePasswordVisibility() {
    this.showPassword = !this.showPassword;
  }

  toggleConfirmPasswordVisibility() {
    this.showConfirmPassword = !this.showConfirmPassword;
  }

  recaptchaValidator(): ValidatorFn {
    return (control: AbstractControl): { [key: string]: any } | null => {
      const recaptchaValue = control.value;
      const isValid = recaptchaValue && recaptchaValue.length > 0; 
      return isValid ? null : { recaptchaInvalid: true };
    };
  }



        signInWithGoogle(): void {
          this.SocialAuthService.signIn(GoogleLoginProvider.PROVIDER_ID).then(
            data => {
              this.social.loginWithGoogle(data.idToken).subscribe(
                res => {
                  

                  if(res.successmessage === "Authentification Successful")
                  {
                    localStorage.setItem('access_token',res.tokens.access_token)
                    localStorage.setItem('refresh_token',res.tokens.refresh_token)
  
                    setTimeout(() => {
                      Swal.fire({
                          title: "Success!",
                          text: "Authentification Reussite",
                          icon: "success",
                          timer: 3000, 
                          showConfirmButton: false
                      });

      
                      this.router.navigate(['/dashboard']);
                  }, 3000);
                 }
                    if(res.errormessage === "Invalid credentials")
                    {

                      this.error = "Vous Avez deja un compte normal"

                    }
                  console.log(res)
                },
                error => {
                  this.error = error ? error : '';
                }
              );
            }
          );
        }


      signInWithFB(): void {
          this.SocialAuthService.signIn(FacebookLoginProvider.PROVIDER_ID).then(
            data => {
              this.social.loginWithFacebook(data.authToken).subscribe(
                res => {
                  
                  if(res.successmessage === "Authentification Successful")
                  {
                    
                    localStorage.setItem('access_token',res.tokens.access_token)
                    localStorage.setItem('refresh_token',res.tokens.refresh_token)
                    setTimeout(() => {
                      Swal.fire({
                          title: "Success!",
                          text: "Authentification Reussite",
                          icon: "success",
                          timer: 3000, 
                          showConfirmButton: false
                      });
                   
                      this.cookieService.set('access_token',res.tokens.access_token);
                      this.cookieService.set('refresh_token',res.tokens.refresh_token);

                      this.router.navigate(['/dashboard']);
                  }, 3000);
                 }
                 
                },
                error => {
                  this.error = error ? error : '';
                }
              );
            }
          );
        }

  signOut(): void {
    this.SocialAuthService.signOut();
    this.user = null;
    this.cdr.detectChanges();

  }

}
