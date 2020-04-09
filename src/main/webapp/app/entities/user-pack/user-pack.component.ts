import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IUserPack } from 'app/shared/model/user-pack.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { UserPackService } from './user-pack.service';
import { UserPackDeleteDialogComponent } from './user-pack-delete-dialog.component';

@Component({
  selector: 'jhi-user-pack',
  templateUrl: './user-pack.component.html'
})
export class UserPackComponent implements OnInit, OnDestroy {
  userPacks: IUserPack[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected userPackService: UserPackService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.userPacks = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.userPackService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IUserPack[]>) => this.paginateUserPacks(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.userPacks = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInUserPacks();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IUserPack): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInUserPacks(): void {
    this.eventSubscriber = this.eventManager.subscribe('userPackListModification', () => this.reset());
  }

  delete(userPack: IUserPack): void {
    const modalRef = this.modalService.open(UserPackDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.userPack = userPack;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateUserPacks(data: IUserPack[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.userPacks.push(data[i]);
      }
    }
  }
}
