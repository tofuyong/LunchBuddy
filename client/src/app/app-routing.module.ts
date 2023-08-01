import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { SignupComponent } from './components/signup/signup.component';

const routes: Routes = [
  { path: '', title: 'Login', component: LoginComponent },
  { path: 'signup', title: 'Sign Up', component: SignupComponent },
  { path: 'dashboard', title: 'Dashboard', component: DashboardComponent },
  { path: '**', redirectTo: '',  pathMatch: 'full'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
