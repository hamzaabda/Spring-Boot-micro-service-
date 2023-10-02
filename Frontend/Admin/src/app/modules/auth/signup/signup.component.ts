import { Component, OnInit } from '@angular/core';
import { AbstractControl, FormBuilder, FormGroup, ValidationErrors, ValidatorFn, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';

import { AuthenticationService } from '../../../core/services/auth.service';
import { environment } from '../../../../environments/environment';
import { first } from 'rxjs/operators';
import { AuthService } from '../service/auth.service';
import Swal from 'sweetalert2';
import { ChangeDetectorRef } from '@angular/core';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.scss']
})
export class SignupComponent implements OnInit {
  showPassword = false;
  showConfirmPassword = false;
  passwordsDoNotMatch: boolean = false;

  signupForm: FormGroup;
  submitted = false;
  error = '';
  successmsg: boolean = false;
  roles = ['PARTICIPANT', 'ORGANISATEUR', 'PARTENAIRE']; 
  capchaKey = environment.capchaKey;
  passwordControl: AbstractControl;
  confirmPasswordControl: AbstractControl;

  // set the currenr year
  year: number = new Date().getFullYear();

  // tslint:disable-next-line: max-line-length
  constructor(private formBuilder: FormBuilder, 
    private route: ActivatedRoute, private router: Router, 
    private authenticationService: AuthenticationService,
    private authservice :AuthService,private cd: ChangeDetectorRef) { }

  ngOnInit() {

    const passwordRegexPattern = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*])(?=.*[a-zA-Z]).{8,}$/;

    this.signupForm = this.formBuilder.group({
      username: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.pattern(passwordRegexPattern), Validators.minLength(8)]],
      confirmPassword: ['', Validators.required],
      firstname : ['', Validators.required],
      lastname : ['', Validators.required],
      role: ['', Validators.required],
      recaptcha: ['', [Validators.required , this.recaptchaValidator()]]
    });


    this.passwordControl = this.signupForm.get('password');
    this.confirmPasswordControl = this.signupForm.get('confirmPassword');
    this.signupForm.setValidators(this.passwordsMatch.bind(this));


  }

  // convenience getter for easy access to form fields
  get f() { return this.signupForm.controls; }

  /**
   * On submit form
   */
  onSubmit() {
    this.submitted = true;

    // stop here if form is invalid
    if (this.signupForm.invalid) {
      return;
    } 
    
    // else {
    //   if (environment.defaultauth === 'firebase') {
    //     this.authenticationService.register(this.f.email.value, this.f.password.value).then((res: any) => {
    //       this.successmsg = true;
    //       if (this.successmsg) {
    //         this.router.navigate(['/dashboard']);
    //       }
    //     })
    //       .catch(error => {
    //         this.error = error ? error : '';
    //       });
    //   }    
    else {
      this.authservice.register(this.signupForm.value)
        .subscribe(
          (data) => {

            if(data && data.successmessage === "registration successful")
            {
              this.successmsg = true;
              Swal.fire({
                title: 'Compte créé avec succès!',
                text: 'Veuillez vérifier votre e-mail pour continuer.',
                icon: 'success',
                confirmButtonText: 'D\'accord'
              })
              setTimeout(() => {
                this.router.navigate(['/account/login']);
              }, 3000);
            }
            else 
             if(data.errormessage === "Email already Exisits")
            {
              this.error = "Ce nom Email existe déjà. Veuillez en choisir un autre.";
              Swal.fire({
                title: 'Erreur!',
                text: 'Ce nom Email existe déjà. Veuillez en choisir un autre.',
                icon: 'error',
                confirmButtonText: 'OK'
              });

            }

            else 
            if(data.errormessage === "User already Exisits")
           {
             this.error = "Ce nom d\'utilisateur existe déjà. Veuillez en choisir un autre.";
             Swal.fire({
               title: 'Erreur!',
               text: 'Ce nom d\'utilisateur existe déjà. Veuillez en choisir un autre.',
               icon: 'error',
               confirmButtonText: 'OK'
             });

           }
           else 
           if(data.errormessage === "An error occurred during registration")
          {
            this.error = "Une erreur imprévue s\'est produite. Veuillez réessayer ultérieurement.";
            Swal.fire({
              title: 'Erreur!',
              text: 'Une erreur imprévue s\'est produite. Veuillez réessayer ultérieurement.',
              icon: 'error',
              confirmButtonText: 'OK'
            });

          }
    
         
          },
          error => {
            Swal.fire({
              title: 'Erreur!',
              text: 'Une erreur imprévue s\'est produite. Veuillez réessayer ultérieurement.',
              icon: 'error',
              confirmButtonText: 'OK'
            }); 
            this.error = "Une erreur imprévue s\'est produite. Veuillez réessayer ultérieurement." ? error : '';
          });
    }
    
    
    }

    togglePasswordVisibility() {
      this.showPassword = !this.showPassword;
    }

    toggleConfirmPasswordVisibility() {
      this.showConfirmPassword = !this.showConfirmPassword;
    }


    passwordsMatch(control: AbstractControl): { [key: string]: any } | null {
      const password = control.get('password').value;
      const confirmPassword = control.get('confirmPassword').value;

      return password !== confirmPassword ? { 'passwordMismatch': true } : null;
    }




    recaptchaValidator(): ValidatorFn {
      return (control: AbstractControl): { [key: string]: any } | null => {
        const recaptchaValue = control.value;
        const isValid = recaptchaValue && recaptchaValue.length > 0; 
        return isValid ? null : { recaptchaInvalid: true };
      };
    }

  

  }


