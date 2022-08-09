import { Component, Inject, OnInit } from '@angular/core';
import { MatDialog, MatDialogConfig, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { NgxUiLoaderService } from 'ngx-ui-loader';
import { ReservationService } from 'src/app/services/reservation.service';
import { SnackbarService } from 'src/app/services/snackbar.service';
import { GlobalConstants } from 'src/app/shared/global-constants';
import { ConfirmationComponent } from '../confirmation/confirmation.component';

@Component({
  selector: 'app-book-sheet',
  templateUrl: './book-sheet.component.html',
  styleUrls: ['./book-sheet.component.scss']
})
export class BookSheetComponent implements OnInit {

  sheetsList: any = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20];

  bookedSheetsList: any = [];

  selectedSheetsByUser: any = [];
  busDetails: any;
  total: number = 0;
  chargePerSeat: any = 0;
  responseMessage: any;
  constructor(private snackbarService: SnackbarService,
    @Inject(MAT_DIALOG_DATA) public dialogData: any,
    public dialogRef: MatDialogRef<BookSheetComponent>,
    private dialog: MatDialog,
    private reservationService: ReservationService,
    private ngxService: NgxUiLoaderService) { }

  ngOnInit(): void {
    // this.ngxService.start();
    this.busDetails = this.dialogData.busDetails;
    console.log(this.dialogData.busDetails);
    this.getBookedSeatsDetails();
  }

  getBookedSeatsDetails() {
    var data = {
      busId: this.busDetails.id,
      date: this.dialogData.dateOfJourney
    }
    this.reservationService.getBookedSeats(data).subscribe((response: any) => {
      this.bookedSheetsList = response;
      for (var i = 0; i < this.bookedSheetsList.length; i++) {
        var value = this.sheetsList.findIndex((e: any) => e == this.bookedSheetsList[i]);
        this.sheetsList.splice(value, 1);
        this.sheetsList = [...this.sheetsList];
      }

      this.chargePerSeat = parseInt(this.dialogData.busDetails.chargePerSeat);
      // this.ngxService.stop();
      console.log(response);
    }, (error: any) => {
      console.log(error);
      this.ngxService.stop();
      if (error.error?.message) {
        this.responseMessage = error.error?.message;
      }
      else {
        this.responseMessage = GlobalConstants.genericError;
      }
      this.snackbarService.openSnackBar(this.responseMessage, GlobalConstants.error);
    })
  }

  addSheet(sheetNumber: any) {
    var sheetNumberTemp = this.selectedSheetsByUser.find((e: any) => e == sheetNumber);
    if (sheetNumberTemp === undefined) {
      this.selectedSheetsByUser.push(sheetNumber);
      this.selectedSheetsByUser = [...this.selectedSheetsByUser];
      this.total = this.total + this.chargePerSeat;
    }
    else {
      this.removeSheet(sheetNumber);
      // this.snackbarService.openSnackBar(GlobalConstants.sheetExistError, GlobalConstants.error);
    }
  }

  removeSheet(sheetNumber: any) {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.data = {
      message: 'remove ' + sheetNumber + ' sheet'
    };
    const dialogRef = this.dialog.open(ConfirmationComponent, dialogConfig);
    const sub = dialogRef.componentInstance.onEmitStatusChange.subscribe((user) => {
      dialogRef.close();
      var sheetNumberTemp = this.selectedSheetsByUser.findIndex((e: any) => e == sheetNumber);
      this.selectedSheetsByUser.splice(sheetNumberTemp, 1);
      this.selectedSheetsByUser = [...this.selectedSheetsByUser];
      this.total = this.total - this.chargePerSeat;
    })
  }

  handleSubmit() {
    var data = {
      busId: this.busDetails.id,
      userId: localStorage.getItem('id'),
      seats: this.selectedSheetsByUser.toString(),
      journyDate: this.dialogData.dateOfJourney,
      status: "Booked",
      fare: this.total.toString()
    }
    console.log(data);
    this.reservationService.reserveSeat(data).subscribe((response:any)=>{
      this.dialogRef.close();
      this.responseMessage = response.message;
      this.snackbarService.openSnackBar(this.responseMessage,"success");
    },(error:any)=>{
      this.dialogRef.close();
      if(error.error?.message){
        this.responseMessage = error.error?.message;
      }
      else{
        this.responseMessage = GlobalConstants.genericError;
      }
      this.snackbarService.openSnackBar(this.responseMessage,GlobalConstants.error);
    })
  }

  validateSubmit() {
    if (this.total === 0)
      return true;

    else
      return false;
  }

}
