import { DatePipe } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { MatTableDataSource } from '@angular/material/table';
import { Router } from '@angular/router';
import { NgxUiLoaderService } from 'ngx-ui-loader';
import { BusService } from 'src/app/services/bus.service';
import { SnackbarService } from 'src/app/services/snackbar.service';
import { GlobalConstants } from 'src/app/shared/global-constants';
import { BookSheetComponent } from '../dialog/book-sheet/book-sheet.component';

@Component({
  selector: 'app-manage-bus-reservation',
  templateUrl: './manage-bus-reservation.component.html',
  styleUrls: ['./manage-bus-reservation.component.scss']
})
export class ManageBusReservationComponent implements OnInit {
  hideOrShow: any;
  displayedColumns: string[] = ['busName', 'departTime', 'arivalTime', 'chargePerSeat', 'edit'];
  dataSource: any;
  responseMessage: any;
  productForm: any = FormGroup;
  searchedDate: any;

  list: any = [];

  constructor(
    private ngxService: NgxUiLoaderService,
    private dialog: MatDialog,
    private snackbarService: SnackbarService,
    private router: Router,
    private formBuilder: FormBuilder,
    public datepipe: DatePipe,
    private busService: BusService) { }

  ngOnInit(): void {
    this.productForm = this.formBuilder.group({
      from: [null, Validators.required],
      to: [null, Validators.required],
      dateOfJourney: [null, Validators.required]
    })
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }

  handleSearchAction() {
    var formData = this.productForm.value;
    let latest_date = this.datepipe.transform(formData.dateOfJourney, 'dd/MM/yyyy');
    this.searchedDate = latest_date?.toString();
    var data = {
      from: formData.from,
      to: formData.to,
      date: latest_date?.toString()
    }

    this.busService.getAllBusByDate(data).subscribe((response: any) => {
      this.list = response;
      if (this.list?.length == 0)
        this.hideOrShow = true;
      else
        this.hideOrShow = false;
      this.ngxService.stop();
      this.dataSource = new MatTableDataSource(response);
    }, (error: any) => {
      this.ngxService.stop();
      console.log(error);
      if (error.error?.message) {
        this.responseMessage = error.error?.message;
      }
      else {
        this.responseMessage = GlobalConstants.genericError;
      }
      this.snackbarService.openSnackBar(this.responseMessage, GlobalConstants.error);
    })
  }

  handleSeatAction(values: any) {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.data = {
      dateOfJourney: this.searchedDate,
      busDetails: values
    }
    dialogConfig.width = "850px";
    const dialogRef = this.dialog.open(BookSheetComponent, dialogConfig);
    this.router.events.subscribe(() => {
      dialogRef.close();
    })
  }

}
