import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { SignupComponent } from './components/signup/signup.component';
import { RequestComponent } from './components/request/request.component';

const routes: Routes = [
  { path: '', title: 'Login', component: LoginComponent },
  { path: 'signup', title: 'Sign Up', component: SignupComponent },
  { path: 'dashboard/:employeeId', title: 'Dashboard', component: DashboardComponent },
  { path: 'request/:employeeId', title: 'New Request', component: RequestComponent },
  { path: '**', redirectTo: '',  pathMatch: 'full'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
