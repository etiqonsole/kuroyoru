package kuroyoru.kcp;

import java.io.Writer;

public class KCPWriter extends Writer {

	append(char c) {}
Writer 	append(CharSequence csq) {}
Writer 	append(CharSequence csq, int start, int end) {}
abstract void 	close() {}
abstract void 	flush() {}
void 	write(char[] cbuf) {}
abstract void 	write(char[] cbuf, int off, int len) {}
void 	write(int c) {}
void 	write(String str) {}
void 	write(String str, int off, int len) {}
}