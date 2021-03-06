package azkaban.project.validator;

import java.util.HashSet;
import java.util.Set;

/**
 * The result of a project validation generated by a {@link ProjectValidator}. It contains
 * an enum of type {@link ValidationStatus} representing whether the validation passes,
 * generates warnings, or generates errors. Accordingly, three sets of String are also
 * maintained, storing the messages generated by the {@link ProjectValidator} at both
 * {@link ValidationStatus#WARN} and {@link ValidationStatus#ERROR} level, as well as
 * information messages associated with both levels.
 */
public class ValidationReport {

  protected ValidationStatus _status;
  protected Set<String> _infoMsgs;
  protected Set<String> _warningMsgs;
  protected Set<String> _errorMsgs;

  public ValidationReport() {
    _status = ValidationStatus.PASS;
    _infoMsgs = new HashSet<String>();
    _warningMsgs = new HashSet<String>();
    _errorMsgs = new HashSet<String>();
  }

  /**
   * Add an information message associated with warning messages
   *
   * @param msgs
   */
  public void addWarnLevelInfoMsg(String msg) {
    if (msg != null) {
      _infoMsgs.add("WARN" + msg);
    }
  }

  /**
   * Add an information message associated with error messages
   *
   * @param msgs
   */
  public void addErrorLevelInfoMsg(String msg) {
    if (msg != null) {
      _infoMsgs.add("ERROR" + msg);
    }
  }

  /**
   * Add a message with status level being {@link ValidationStatus#WARN}
   *
   * @param msgs
   */
  public void addWarningMsgs(Set<String> msgs) {
    if (msgs != null) {
      _warningMsgs.addAll(msgs);
      if (!msgs.isEmpty() && _errorMsgs.isEmpty()) {
        _status = ValidationStatus.WARN;
      }
    }
  }

  /**
   * Add a message with status level being {@link ValidationStatus#ERROR}
   *
   * @param msgs
   */
  public void addErrorMsgs(Set<String> msgs) {
    if (msgs != null) {
      _errorMsgs.addAll(msgs);
      if (!msgs.isEmpty()) {
        _status = ValidationStatus.ERROR;
      }
    }
  }

  /**
   * Retrieve the status of the report.
   *
   * @return
   */
  public ValidationStatus getStatus() {
    return _status;
  }

  /**
   * Retrieve the list of information messages.
   *
   * @return
   */
  public Set<String> getInfoMsgs() {
    return _infoMsgs;
  }

  /**
   * Retrieve the messages associated with status level {@link ValidationStatus#WARN}
   *
   * @return
   */
  public Set<String> getWarningMsgs() {
    return _warningMsgs;
  }

  /**
   * Retrieve the messages associated with status level {@link ValidationStatus#ERROR}
   *
   * @return
   */
  public Set<String> getErrorMsgs() {
    return _errorMsgs;
  }

  /**
   * Return the severity level this information message is associated with.
   *
   * @param msg
   * @return
   */
  public static ValidationStatus getInfoMsgLevel(String msg) {
    if (msg.startsWith("ERROR")) {
      return ValidationStatus.ERROR;
    }
    if (msg.startsWith("WARN")) {
      return ValidationStatus.WARN;
    }
    return ValidationStatus.PASS;
  }

  /**
   * Get the raw information message.
   *
   * @param msg
   * @return
   */
  public static String getInfoMsg(String msg) {
    if (msg.startsWith("ERROR")) {
      return msg.replaceFirst("ERROR", "");
    }
    if (msg.startsWith("WARN")) {
      return msg.replaceFirst("WARN", "");
    }
    return msg;
  }
}
