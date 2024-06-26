import { ClientGuard } from './guard/client.guard';
import { ReportPageComponent } from './../shared/pages/report-page/report-page.component';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { RideRequestPageComponent } from 'src/app/modules/client/pages/ride-request-page/ride-request-page.component';
import { HistoryComponent } from '../shared/components/history/history.component';
import { UserProfilePageComponent } from '../shared/pages/user-profile-page/user-profile-page.component';
import { ClientDashboardComponent } from './pages/client-dashboard/client-dashboard.component';
import {LogsComponent} from "../shared/pages/logs/logs.component";

const routes: Routes = [
  {
    path: 'request-ride-page',
    component: RideRequestPageComponent,
    outlet: 'ClientRouter',
    canActivate: [ClientGuard],
  },
  {
    path: 'profile-page',
    component: UserProfilePageComponent,
    outlet: 'ClientRouter',
    canActivate: [ClientGuard],
  },
  {
    path: 'history',
    component: HistoryComponent,
    outlet: 'ClientRouter',
    canActivate: [ClientGuard],
  },
  {
    path: 'report',
    component: ReportPageComponent,
    outlet: 'ClientRouter',
    canActivate: [ClientGuard],
  },
  {
    path: 'logs',
    component: LogsComponent,
    outlet: 'ClientRouter',
    canActivate: [ClientGuard],
  },

];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ClientRoutingModule { }
