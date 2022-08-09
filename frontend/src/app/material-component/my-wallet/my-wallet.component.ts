import { Component, OnInit } from '@angular/core';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { MatTableDataSource } from '@angular/material/table';
import { Router } from '@angular/router';
import { NgxUiLoaderService } from 'ngx-ui-loader';
import { SnackbarService } from 'src/app/services/snackbar.service';
import { WalletService } from 'src/app/services/wallet.service';
import { GlobalConstants } from 'src/app/shared/global-constants';
import { AddMoneyComponent } from '../dialog/add-money/add-money.component';

@Component({
  selector: 'app-my-wallet',
  templateUrl: './my-wallet.component.html',
  styleUrls: ['./my-wallet.component.scss']
})
export class MyWalletComponent implements OnInit {
  displayedColumns:string[] = ['name','edit'];
  dataSource:any;
  balance:any;
  responseMessage:any;
  constructor(
    private ngxService:NgxUiLoaderService,
    private dialog:MatDialog,
    private snackbarService:SnackbarService,
    private router:Router,
    private walletService:WalletService) { }

  ngOnInit(): void {
    this.ngxService.start();
    this.getAmountByUserId();
  }

  getAmountByUserId(){
    this.walletService.getAmountByUserId(localStorage.getItem('id')).subscribe((response:any)=>{
      this.ngxService.stop();
      this.balance = response.amount;
    },(error:any)=>{
      this.ngxService.stop();
      if(error.error?.message){
        this.responseMessage = error.error?.message;
      }
      else{
        this.responseMessage = GlobalConstants.genericError;
      }
      this.snackbarService.openSnackBar(this.responseMessage,GlobalConstants.error);
    })
  }

  handleAddAction(){
    const dialogConfig = new MatDialogConfig();
    dialogConfig.data = {
      action: 'Add'
    }
    dialogConfig.width = "550px";
    const dialogRef = this.dialog.open(AddMoneyComponent,dialogConfig);
    this.router.events.subscribe(()=>{
      dialogRef.close();
    });
    const sub = dialogRef.componentInstance.onAddMoney.subscribe(
      (response)=>{
        this.getAmountByUserId();
      }
    )
  }
}
