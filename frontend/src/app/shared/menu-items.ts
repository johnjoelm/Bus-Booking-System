import { Injectable } from "@angular/core";

export interface Menu {
    state: string;
    name: string;
    icon: string;
    role: string;
}

const MENUITEMS = [
    { state: 'dashboard', name: 'Dashboard', icon: 'dashboard', role: '' },
    { state: 'bus', name: 'Manage Bus', icon: 'category', role: 'admin' },
    { state: 'busReservation', name: 'Book Tickets', icon: 'inventory_2', role: 'user' },
    { state: 'bookedTicket', name: 'Booked Ticket', icon: 'list_alt', role: 'user' },
    { state: 'cancelledTicket', name: 'Cancelled Ticket', icon: 'cancel', role: 'user' },
    { state: 'myWallet', name: 'My Wallet', icon: 'account_balance_wallet', role: 'user' }
];

@Injectable()
export class MenuItems {
    getMenuitem(): Menu[] {
        return MENUITEMS;
    }
}