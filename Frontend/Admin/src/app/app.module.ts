import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NgModule } from '@angular/core';
import { HttpClientModule, HTTP_INTERCEPTORS, HttpClient } from '@angular/common/http';

import { environment } from '../environments/environment';

import { NgbNavModule, NgbAccordionModule, NgbTooltipModule, NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { CarouselModule } from 'ngx-owl-carousel-o';
import { ScrollToModule } from '@nicky-lenaers/ngx-scroll-to';

import { SharedModule } from './cyptolanding/shared/shared.module';
import {CookieService} from 'ngx-cookie-service';

import { ExtrapagesModule } from './extrapages/extrapages.module';

import { LayoutsModule } from './layouts/layouts.module';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { initFirebaseBackend } from './authUtils';
import { CyptolandingComponent } from './cyptolanding/cyptolanding.component';
import { TranslateModule, TranslateLoader } from '@ngx-translate/core';
import { TranslateHttpLoader } from '@ngx-translate/http-loader';

import { ErrorInterceptor } from './core/helpers/error.interceptor';
import { JwtInterceptor } from './core/helpers/jwt.interceptor';
import { FakeBackendInterceptor } from './core/helpers/fake-backend';
import { SocialAuthServiceConfig, SocialLoginModule } from 'angularx-social-login';
import {
  GoogleLoginProvider,
  FacebookLoginProvider
} from 'angularx-social-login';
import { AuthInterceptor } from './modules/auth.interceptor';

if (environment.defaultauth === 'firebase') {
  initFirebaseBackend(environment.firebaseConfig);
} else {
  // tslint:disable-next-line: no-unused-expression
  FakeBackendInterceptor;
}

export function createTranslateLoader(http: HttpClient): any {
  return new TranslateHttpLoader(http, 'assets/i18n/', '.json');
}
const googleLoginOptions = {
  scope: 'profile email',
  plugin_name:'login' //you can use any name here
}; 
@NgModule({
  declarations: [
    AppComponent,
    CyptolandingComponent,
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    HttpClientModule,
    TranslateModule.forRoot({
      loader: {
        provide: TranslateLoader,
        useFactory: createTranslateLoader,
        deps: [HttpClient]
      }
    }),
    LayoutsModule,
    AppRoutingModule,
    ExtrapagesModule,
    CarouselModule,
    NgbAccordionModule,
    NgbNavModule,
    NgbTooltipModule,
    SharedModule,
    ScrollToModule.forRoot(),
    NgbModule,SocialLoginModule
  ],
  bootstrap: [AppComponent],
  // providers: [
  //   // { provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true },
  //   // { provide: HTTP_INTERCEPTORS, useClass: ErrorInterceptor, multi: true },
  //   // { provide: HTTP_INTERCEPTORS, useClass: FakeBackendInterceptor, multi: true },
  //   // LoaderService,
  //   // { provide: HTTP_INTERCEPTORS, useClass: LoaderInterceptorService, multi: true },
  //   {
  //     provide: 'SocialAuthServiceConfig',
  //     useValue: {
  //       autoLogin: false,
  //       providers: [
  //         {
  //           id: GoogleLoginProvider.PROVIDER_ID,
  //           provider: new GoogleLoginProvider('965604014795-etfe1bs6e1sl84pr4c4fjkgf43i1lbal.apps.googleusercontent.com')
  //         },
  //         {
  //           id: FacebookLoginProvider.PROVIDER_ID,
  //           provider: new FacebookLoginProvider('778001543884518')
  //         }
  //       ],
  //       onError: (err) => {
  //         console.error(err);
  //       }
  //     } 
  //   }
  // ]

  
  providers: [
    {
      provide: 'SocialAuthServiceConfig',
      useValue: {
        autoLogin: false,
        providers: [
          {
            id: GoogleLoginProvider.PROVIDER_ID,
            provider: new GoogleLoginProvider(
              '965604014795-etfe1bs6e1sl84pr4c4fjkgf43i1lbal.apps.googleusercontent.com',googleLoginOptions
            )
          },
          {
            id: FacebookLoginProvider.PROVIDER_ID,
            provider: new FacebookLoginProvider('778001543884518')
          }
        ],
        onError: (err) => {
          console.error(err);
        }
      } as SocialAuthServiceConfig,
    }
    ,CookieService,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptor, // Use your interceptor here
      multi: true,
    }
//    { provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true },
//  { provide: HTTP_INTERCEPTORS, useClass: ErrorInterceptor, multi: true },
  // { provide: HTTP_INTERCEPTORS, useClass: FakeBackendInterceptor, multi: true },
  //  LoaderService,
  //  { provide: HTTP_INTERCEPTORS, useClass: LoaderInterceptorService, multi: true }
  ]
  ,
})
export class AppModule { }
