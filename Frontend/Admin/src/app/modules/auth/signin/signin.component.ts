import { Component, OnInit } from '@angular/core';
import { AbstractControl, FormBuilder, FormGroup, ValidatorFn, Validators } from '@angular/forms';

import { AuthenticationService } from '../../../core/services/auth.service';
import { AuthfakeauthenticationService } from '../../../core/services/authfake.service';

import { ActivatedRoute, Router } from '@angular/router';
import { first } from 'rxjs/operators';
import {Title} from "@angular/platform-browser";

import { environment } from '../../../../environments/environment';
import { AuthService } from '../service/auth.service';
import Swal from "sweetalert2"
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

  // tslint:disable-next-line: max-line-length

  constructor(private formBuilder: FormBuilder, private route: ActivatedRoute, private router: Router, private authenticationService: AuthenticationService,
    private authFackservice: AuthfakeauthenticationService, 
    
    private authservice:AuthService,
    private titleService:Title) {

      this.titleService.setTitle("CulTechConnect |  Login");
     }

    ngOnInit() {
      this.loginForm = this.formBuilder.group({
        username: ['', [Validators.required]],
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
      // if (environment.defaultauth === 'firebase') {
      //   this.authenticationService.login(this.f.email.value, this.f.password.value).then((res: any) => {
      //     this.router.navigate(['/dashboard']);
      //   })
      //     .catch(error => {
      //       this.error = error ? error : '';
      //     });
      // } 
        this.authservice.login(this.loginForm.value)
          .pipe(first())
          .subscribe(
            (data) => {

              if(data.successmessage === "Authentification Successful")
              {
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

            if(data.errormessage === "Username Not found")
            {
              this.error = "Utilisateur n existe pas"
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

}
