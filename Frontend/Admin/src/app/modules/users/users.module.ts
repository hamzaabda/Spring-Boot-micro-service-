import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UserlistComponent } from './userlist/userlist.component';

import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { NgApexchartsModule } from 'ng-apexcharts';
import { NgbTooltipModule } from '@ng-bootstrap/ng-bootstrap';
import { NgSelectModule } from '@ng-select/ng-select';

import { WidgetModule } from '../../shared/widget/widget.module';
import { UIModule } from '../../shared/ui/ui.module';
import { UsersRoutingModule } from './users-routing.module';
import { EdituserComponent } from './edituser/edituser.component';
import { ProfileComponent } from './profile/profile.component';


@NgModule({
  declarations: [
    UserlistComponent,
    EdituserComponent,
    ProfileComponent
  ],
  imports: [
    CommonModule,
    UsersRoutingModule,
    WidgetModule,
    UIModule,
    NgSelectModule,
    NgApexchartsModule,
    FormsModule, ReactiveFormsModule ,
    NgbTooltipModule
  ]
})
export class UsersModule { }
