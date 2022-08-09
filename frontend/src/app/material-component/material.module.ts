import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { HttpClientModule } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { CdkTableModule } from '@angular/cdk/table';

import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { FlexLayoutModule } from '@angular/flex-layout';

import { MaterialRoutes } from './material.routing';
import { MaterialModule } from '../shared/material-module';
import { ConfirmationComponent } from './dialog/confirmation/confirmation.component';
import { ChangePasswordComponent } from './dialog/change-password/change-password.component';
import { ManageBusComponent } from './manage-bus/manage-bus.component';
import { BusComponent } from './dialog/bus/bus.component';
import { ManageBusReservationComponent } from './manage-bus-reservation/manage-bus-reservation.component';
import { BookSheetComponent } from './dialog/book-sheet/book-sheet.component';
import { BookedTicketComponent } from './booked-ticket/booked-ticket.component';
import { CancelledTicketComponent } from './cancelled-ticket/cancelled-ticket.component';
import { ProfileComponent } from './dialog/profile/profile.component';
import { MyWalletComponent } from './my-wallet/my-wallet.component';
import { AddMoneyComponent } from './dialog/add-money/add-money.component';

@NgModule({
  imports: [
    CommonModule,
    RouterModule.forChild(MaterialRoutes),
    MaterialModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    FlexLayoutModule,
    CdkTableModule
  ],
  providers: [],
  declarations: [
    ConfirmationComponent,
    ChangePasswordComponent,
    ManageBusComponent,
    BusComponent,
    ManageBusReservationComponent,
    BookSheetComponent,
    BookedTicketComponent,
    CancelledTicketComponent,
    ProfileComponent,
    MyWalletComponent,
    AddMoneyComponent    
  ]
})
export class MaterialComponentsModule {}
