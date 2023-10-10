import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { UserlistComponent } from './userlist/userlist.component';
import { ProfileComponent } from './profile/profile.component';

const routes: Routes = [
    {
        path: 'list',
        component: UserlistComponent
    },
    // {
    //     path: 'grid',
    //     component: UsergridComponent
    // },
    {
        path: 'profile',
        component: ProfileComponent
    }
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class UsersRoutingModule { }