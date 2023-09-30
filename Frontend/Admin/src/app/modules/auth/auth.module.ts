import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SignupComponent } from './signup/signup.component';
import { AuthRoutingModule } from './auth-routing.module';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';

import { CarouselModule } from 'ngx-owl-carousel-o';
import { NgxCaptchaModule } from 'ngx-captcha';

import { UIModule } from '../../shared/ui/ui.module';
import { NgbAlertModule } from '@ng-bootstrap/ng-bootstrap';
import { SigninComponent } from './signin/signin.component';

@NgModule({
  declarations: [
    SignupComponent,
    SigninComponent
  ],
  imports: [
    CommonModule,
    AuthRoutingModule,
    ReactiveFormsModule,
    FormsModule,
    NgbAlertModule,
    UIModule,
    CarouselModule,NgxCaptchaModule
    ]
})
export class AuthModule { 



}
