import { Routes } from '@angular/router';
import { DashboardComponent } from '../dashboard/dashboard.component';
import { RouteGuardService } from '../services/route-guard.service';
import { BookedTicketComponent } from './booked-ticket/booked-ticket.component';
import { CancelledTicketComponent } from './cancelled-ticket/cancelled-ticket.component';
import { ManageBusReservationComponent } from './manage-bus-reservation/manage-bus-reservation.component';
import { ManageBusComponent } from './manage-bus/manage-bus.component';
import { MyWalletComponent } from './my-wallet/my-wallet.component';



export const MaterialRoutes: Routes = [
    {
        path:'bus',
        component:ManageBusComponent,
        canActivate:[RouteGuardService]
    },
    {
        path:'busReservation',
        component:ManageBusReservationComponent,
        canActivate:[RouteGuardService]
    },
    {
        path:'bookedTicket',
        component:BookedTicketComponent,
        canActivate:[RouteGuardService]
    },
    {
        path:'cancelledTicket',
        component:CancelledTicketComponent,
        canActivate:[RouteGuardService]
    },
    {
        path:'myWallet',
        component:MyWalletComponent,
        canActivate:[RouteGuardService]
    }
];
