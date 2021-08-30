import { WebPlugin } from '@capacitor/core';

import type { FileOpenerOptions, FileOpenerPlugin } from './definitions';

export class FileOpenerWeb extends WebPlugin implements FileOpenerPlugin {
  open(options: FileOpenerOptions): Promise<void> {
    console.log('open', options);

    throw this.unimplemented('Not implemented on web.');
  }
}
